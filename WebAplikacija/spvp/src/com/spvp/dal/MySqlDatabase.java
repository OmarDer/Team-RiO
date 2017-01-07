/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.dal;

import com.spvp.model.Grad;
import com.spvp.model.Location;
import com.spvp.model.Prognoza;
import com.spvp.services.IWebService;
import com.spvp.services.LocationService;
import com.spvp.services.WebService;
import com.spvp.services.WorldWeatherOnlineWebService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Ragib Smajic
 */
public class MySqlDatabase implements IDatabase {
    
    private DataSource dataSource;
    private WebService webService;

    public void setWebService(IWebService webService) {
        this.webService = new WebService(webService);
    }
    
    private static MySqlDatabase db = null;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    private MySqlDatabase(){}
    
    public static MySqlDatabase getInstance(){
        
        if(db == null){
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
            db = ctx.getBean("mysqlDb", MySqlDatabase.class);
        }
        return db;
    }
    
    private Connection getConnection() throws SQLException{
        
        return dataSource.getConnection();
    }
    
    @Override
    public Boolean ucitajGradoveUBazu(ArrayList<Grad> gradovi) throws SQLException{
        
        Connection conn = null;
        Boolean status = false;
        
        try {
                conn = getConnection();
                
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO gradovi (ime, longitude, latitude, veci_centar)"
                                                                + "VALUES(?,?,?,?)");
                for(Grad x : gradovi){

                    pstmt.clearParameters();
                    pstmt.setString(1, x.getImeGrada());
                    pstmt.setDouble(2, x.getLongitude());
                    pstmt.setDouble(3, x.getLatitude());
                    pstmt.setBoolean(4, x.getVeciCentar());

                    pstmt.addBatch();
                }

                pstmt.executeBatch();
                status = true;
               
        
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
           
        }finally{
        
            if(conn != null)
                conn.close();
        } 
        
        return status;
    }
    
    @Override
    public Boolean ucitajPrognozeUBazu(ArrayList<Prognoza> prognoze) throws SQLException{
        
        Connection conn = null;
        Boolean status = false;
        
        try {
                conn = getConnection();
                conn.setAutoCommit(false);
                
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery("SELECT AUTO_INCREMENT " +
                                              "FROM  INFORMATION_SCHEMA.TABLES " +
                                              "WHERE TABLE_SCHEMA = 'weather_forecasting' " +
                                              "AND   TABLE_NAME   = 'historija_prognoze';");
                
                int zadnjiId = -1;
                
                rs.next();
		zadnjiId = rs.getInt("AUTO_INCREMENT");
		
                int idGrada = -1;
                
                
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO historija_prognoze (id, vrijeme, temp, pritisak, brzina_vjetra, vlaznost_zraka, datum) "
                                                                + "VALUES(?, ?,?,?,?,?,?)");              
                
                PreparedStatement pstmt3 = conn.prepareStatement("INSERT INTO gradovi_prognoze (prognoza_id, grad_id) "
                                                                        + "VALUES(?,?)");
                
                for(Prognoza x : prognoze){

                    pstmt.clearParameters();
                    pstmt.setInt(1, zadnjiId);
                    pstmt.setString(2, x.getVrijeme());
                    pstmt.setString(3, x.getTemperatura());
                    pstmt.setString(4, x.getPritisakZraka());
                    pstmt.setString(5, x.getBrzinaVjetra());
                    pstmt.setString(6, x.getVlaznostZraka());
                    pstmt.setDate(7, new java.sql.Date(x.getDatum().getTime()));
                    pstmt.addBatch();
                    
                    idGrada = dajIdGradaPoImenu(x.getZaGrad().getImeGrada());
                    
                    pstmt3.clearParameters();
                    pstmt3.setInt(1, zadnjiId);
                    pstmt3.setInt(2, idGrada);
                    
                    pstmt3.addBatch();
                    
                    zadnjiId++;
                }

                pstmt.executeBatch();
                pstmt3.executeBatch();
                
                
                conn.commit();
                status = true;
        
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
           
        }finally{
        
            if(conn != null)
                conn.close();
        } 
        
        return status;
    }
    
    @Override
    public Grad dajGradPoImenu(String imeGrada) throws SQLException{
        
        String ime;
        Double longitude;
        Double latitude;
        Boolean veciCentar;
        try (Connection conn = getConnection()) {
            
            PreparedStatement ps = conn.prepareStatement("SELECT ime, longitude, latitude, veci_centar "
                    + "FROM gradovi "
                    + "WHERE ime=?");
            ps.setString(1, imeGrada);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ime = rs.getString("ime");
            longitude = rs.getDouble("longitude");
            latitude = rs.getDouble("latitude");
            veciCentar = rs.getBoolean("veci_centar");
        }
        
        return new Grad(ime, longitude, latitude, veciCentar);
        
    }
    
    private int dajIdGradaPoImenu(String imeGrada) throws SQLException{
        
        int x;
        try (Connection conn = getConnection()) {
            
            PreparedStatement ps = conn.prepareStatement("SELECT id " +
                                                            "FROM gradovi " +
                                                            "WHERE LOWER(ime) LIKE LOWER(?)");
            ps.setString(1, imeGrada);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                x = rs.getInt("id");
            else return -1;
        }
        
        return x;
        
    }
    
    @Override
    public ArrayList<Prognoza> dajHistorijskePodatkePrognozaZaGrad(String grad, int brojDana) throws SQLException{
        
        Grad x = this.dajGradPoImenu(grad);
        x.setIdGrada(this.dajIdGradaPoImenu(grad));
        
        this.osvjeziHistorijuPrognozaZaGrad(x, webService);
        
        ArrayList<Prognoza> listaPrognoza = new ArrayList<>();
        
        int idGrada = dajIdGradaPoImenu(grad);
        
        if(idGrada == -1) return listaPrognoza;
        
        try (Connection conn = getConnection()) {
            
            PreparedStatement s = conn.prepareStatement("SELECT vrijeme, temp, pritisak, brzina_vjetra, vlaznost_zraka, datum "
                                                        + "FROM historija_prognoze p, gradovi_prognoze "
                                                        + "WHERE p.id = prognoza_id AND grad_id =? "
                                                        + "ORDER BY datum DESC "
                                                        + "LIMIT ?");
            
            s.setInt(1, idGrada);
            s.setInt(2, brojDana);
            
            ResultSet rs = s.executeQuery();
            
            Grad g = dajGradPoImenu(grad);
            Prognoza p;
            while(rs.next()){
                
                p = new Prognoza(g, rs.getString("temp"), rs.getString("vlaznost_zraka"), rs.getString("pritisak"), rs.getString("brzina_vjetra"), rs.getString("vrijeme"));
                p.setDatum(rs.getDate("datum"));
                listaPrognoza.add(p);
                
            }
        }
        
        return listaPrognoza;
        
    }
    
    private void osvjeziHistorijuPrognozaZaGrad(Grad g, WebService ws){
        
        try (Connection conn = getConnection()) {
              
              PreparedStatement ps = conn.prepareStatement("SELECT hp.datum dat "
                                                          + "FROM historija_prognoze hp, gradovi_prognoze gp "
                                                          + "WHERE hp.id = gp.prognoza_id AND gp.grad_id = ? "
                                                          + "ORDER BY hp.datum DESC "
                                                          + "LIMIT 1");
            
              ps.setInt(1, g.getIdGrada());
              
              ResultSet rs = ps.executeQuery();
              
              if(rs.next()){
                  
                  
                  Calendar cal = Calendar.getInstance();
                  cal.setTime(rs.getDate("dat"));
                  cal.set(Calendar.MILLISECOND, 0);
                  cal.set(Calendar.SECOND, 0);
                  cal.set(Calendar.MINUTE, 0);
                  cal.set(Calendar.HOUR, 0);
                  
                  //System.out.println("Zadnji datum u bazi je bio: " + cal.getTime().toString());
                  
                  Calendar tempDate = Calendar.getInstance();
                  tempDate.set(Calendar.MILLISECOND, 0);
                  tempDate.set(Calendar.SECOND, 0);
                  tempDate.set(Calendar.MINUTE, 0);
                  tempDate.set(Calendar.HOUR, 0);
                  
                  //System.out.println("Danasnji datum je: " + tempDate.getTime().toString());
                  
                  long diff = tempDate.getTime().getTime() - cal.getTime().getTime();
                  long brDana = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                  
                  //System.out.println("Razlika u danima je: " + brDana);
                  
                  if(brDana == 0) return;
                  
                  
                  //System.out.println("Osvjezavanje zadnjeg dana u bazi...");
                  
                  this.osvjeziZadnjuPrognozuZaGrad(g.getImeGrada(), ws);
                  
                  //System.out.println("Ucitavanje novih prognoza...");
                  
                  Location l = new Location(false);
                  l.setCity(g.getImeGrada());
                  l.setCountry("Bosnia and Herzegovina");
                  l.setCountryCode("ba");
                  l.setLatitude(g.getLatitude());
                  l.setLongitude(g.getLongitude());
                  l.setStatus(Boolean.TRUE);
                  
                  this.ucitajPrognozeUBazu(ws.getHistorijskePodatkeByLocation(l, (int) brDana));       
              
              }else{ // Ne postoji ni jedan unos prognoze za dati grad
                  
                  //System.out.println("Zadnji datum u bazi je bio: " + rs.getDate("dat").toString());
                  
                  Location l = new Location(true);
                  l.setCity(g.getImeGrada());
                  l.setCountryCode("ba");
                  l.setLatitude(g.getLatitude());
                  l.setLongitude(g.getLongitude());
                  l.setCountry("Bosnia and Herzegovina");
                  
                  this.ucitajPrognozeUBazu(ws.getHistorijskePodatkeByLocation(l, 15));
                  
              }

            
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void osvjeziHistorijuPrognoza(WebService ws) throws SQLException, ParseException{
        
        try (Connection conn = getConnection()) {
              
              Statement s = conn.createStatement();
              
              ResultSet rs = s.executeQuery("SELECT id, ime, longitude, latitude "
                                           + "FROM gradovi");
              
              Grad x;
              while(rs.next()){
              
                  x = new Grad(rs.getString("ime"), rs.getDouble("longitude"), rs.getDouble("latitude"));
                  x.setIdGrada(rs.getInt("id"));
                  
                  this.osvjeziHistorijuPrognozaZaGrad(x, ws);
              }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public static void main(String[] args) throws SQLException, ParseException{
         
        //MySqlDatabase db = new MySqlDatabase();
        //WebService ws = new WebService(new WorldWeatherOnlineWebService());
        //Grad g = LocationService.getLocationByCityName("Mostar").getGrad();
        //g.setIdGrada(db.dajIdGradaPoImenu("Sarajevo"));
        //db.osvjeziHistorijuPrognozaZaGrad(g, ws);
        //db.osvjeziZadnjuPrognozuZaGrad("Sarajevo", ws);
        //db.osvjeziHistorijuPrognoza(ws);
        //for(int i = 0; i < 500; i++)
        //System.out.println(db.dajIdGradaPoImenu("Sarajevo"));
        //db.ucitajGradoveUBazu(db.dajVeceCentre());
        //db.ucitajPrognozeUBazu(db.dajPrognozeZaVeceCentre());      
        
    }
    
    @Override
    public void osvjeziZadnjuPrognozuZaGrad(String imeGrada, WebService ws) throws SQLException, ParseException{
        
        int id = this.dajIdGradaPoImenu(imeGrada);
        
        try(Connection conn = getConnection()){
        
            PreparedStatement ps = conn.prepareStatement("SELECT hp.id id, hp.datum datum " +
                                                         "FROM historija_prognoze hp, gradovi_prognoze gp " +
                                                         "WHERE hp.id = gp.prognoza_id AND gp.grad_id = ? AND hp.datum =(SELECT thp.datum " +
                                                                                                                        "FROM historija_prognoze thp, gradovi_prognoze tgp " +
                                                                                                                        "WHERE thp.id = tgp.prognoza_id AND tgp.grad_id = ? " +
                                                                                                                        "ORDER BY thp.datum DESC " +
                                                                                                                        "LIMIT 1 " +
                                                                                                                        ")");
            
            ps.setInt(1, id);
            ps.setInt(2, id);
            
            ResultSet rs = ps.executeQuery();
            int idPrognoze = -1;
            Calendar cal = null;
            if(rs.next()){
                idPrognoze = rs.getInt("id");
                cal = Calendar.getInstance();
                cal.setTime(rs.getDate("datum"));
            }
            else
                return;
            
            Location l = LocationService.getLocationByCityName(imeGrada);
            
            Prognoza prognoza = ws.getHistorijskePodatkeByLocationOnSpecificDate(l, cal);
            
            ps = conn.prepareStatement("UPDATE historija_prognoze "
                                    + "SET vrijeme = ?, "
                                    + "    temp = ?, "
                                    + "    pritisak = ?, "
                                    + "    brzina_vjetra = ?, "
                                    + "    vlaznost_zraka = ? "
                                    + "WHERE id = ?");
            
            ps.setString(1, prognoza.getVrijeme());
            ps.setString(2, prognoza.getTemperatura());
            ps.setString(3, prognoza.getPritisakZraka());
            ps.setString(4, prognoza.getBrzinaVjetra());
            ps.setString(5, prognoza.getVlaznostZraka());
            ps.setInt(6, idPrognoze);
            
            ps.executeUpdate();
            
            
            ps.close();

        }
    }
    
    private ArrayList<Grad> dajVeceCentre(){
        
        ArrayList<Grad> gradovi = new ArrayList<>();
        Grad g = LocationService.getLocationByCityName("Sarajevo").getGrad();
        g.setVeciCentar(Boolean.TRUE);
        gradovi.add(g);
        g = LocationService.getLocationByCityName("Zenica").getGrad();
        g.setVeciCentar(Boolean.TRUE);
        gradovi.add(g);
        g = LocationService.getLocationByCityName("Tuzla").getGrad();
        g.setVeciCentar(Boolean.TRUE);
        gradovi.add(g);
        g = LocationService.getLocationByCityName("Mostar").getGrad();
        g.setVeciCentar(Boolean.TRUE);
        gradovi.add(g);
        g = LocationService.getLocationByCityName("Banja Luka").getGrad();
        g.setVeciCentar(Boolean.TRUE);
        gradovi.add(g);
        
        return gradovi;
    
    }
    
    private ArrayList<Prognoza> dajPrognozeZaVeceCentre() throws ParseException{
        
        WebService ws = new WebService(new WorldWeatherOnlineWebService());
        ArrayList<Prognoza> prognoze;
        ArrayList<Prognoza> sve = new ArrayList<>();
        Location l = LocationService.getLocationByCityName("Sarajevo");
        prognoze = ws.getHistorijskePodatkeByLocation(l, 10);
        sve.addAll(prognoze);
        //l.setCity("Zenica");
        l = LocationService.getLocationByCityName("Zenica");
        prognoze = ws.getHistorijskePodatkeByLocation(l, 10);
        sve.addAll(prognoze);
        //l.setCity("Mostar");
        l = LocationService.getLocationByCityName("Mostar");
        prognoze = ws.getHistorijskePodatkeByLocation(l, 10);
        sve.addAll(prognoze);
        //l.setCity("Tuzla");
        l = LocationService.getLocationByCityName("Tuzla");
        prognoze = ws.getHistorijskePodatkeByLocation(l, 10);
        sve.addAll(prognoze);
        //l.setCity("Banja Luka");
        l = LocationService.getLocationByCityName("Banja Luka");
        prognoze = ws.getHistorijskePodatkeByLocation(l, 10);
        sve.addAll(prognoze);
        
        return sve;
    }
       
    
    
}
