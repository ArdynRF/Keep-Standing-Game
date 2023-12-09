package model;

import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 * Mewakili tabel pengguna dalam database.
 * Mewarisi kelas DB untuk berinteraksi dengan database.
 */
public class TableUser extends DB {
    private String tableName; // Nama tabel

    public TableUser() throws Exception, SQLException{
        // Konstruktor
        super();
        this.tableName = "player";
    }

    public void getTUser(){
        // Menjalankan query untuk mengambil semua data dari tabel pengguna
        try {
            String query = "SELECT * from " + this.tableName;
            createQuery(query);
        } catch (Exception e) {
            // Menampilkan error
            System.out.println(e.toString());
        }
    }

    public void getDataTUser(String username) {
        // Menjalankan query untuk mengambil satu record berdasarkan username
        try {
            String query = "SELECT * from " + this.tableName +" WHERE username='" + username + "'";
            createQuery(query);
        } catch (Exception e) {
            // Menampilkan error
            System.err.println(e.toString());
        }
    }

    public void insertData(String username, int score, int standing){
        // Memeriksa apakah perlu dilakukan update
        boolean update = false;
        try {
            TableUser temp = new TableUser();
            temp.getDataTUser(username);
            // Memeriksa apakah username sudah ada dalam database
            if(temp.getResult().next()) {
                update = true;
            } else {
                update = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Untuk operasi insert
        if(!update){
            try {
                String query = "INSERT INTO " + this.tableName + " VALUES(null, '" + username + "', " + Integer.toString(score) + ", " + Integer.toString(standing) + ")";
                createUpdate(query);
            } catch (Exception e) {
                // Menampilkan error
                System.out.println(e.toString());
            }
        }
        // Untuk operasi update
        else if(update){
            try {
                String query = "UPDATE " + this.tableName + " SET score=" + score + ", standing=" + standing + " WHERE username='" + username + "'";
                createUpdate(query);
            } catch (Exception e) {
                // Menampilkan error
                System.out.println(e.getMessage());
            }
        }
    }
    
    // Membuat objek datatable
    public DefaultTableModel setTable(){
        
        DefaultTableModel dataTable = null;
        try{
            // Membuat header tabel
            Object[] column = {"Username", "Score", "Standing"};
            dataTable = new DefaultTableModel(null, column);
            
            // Query data yang akan ditampilkan di tabel
            String query = "SELECT * from " + this.tableName + " ORDER BY score DESC";
            this.createQuery(query);
            // Mengambil data per baris
            while(this.getResult().next()){
                Object[] row = new Object[3];
                // Mengambil data per kolom
                row[0] = this.getResult().getString(2);
                row[1] = this.getResult().getString(3);
                row[2] = this.getResult().getString(4);
                dataTable.addRow(row);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        // Mengembalikan data yang telah diambil
        return dataTable;
    }
}
