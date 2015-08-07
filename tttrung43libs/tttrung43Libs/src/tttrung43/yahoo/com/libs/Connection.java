package tttrung43.yahoo.com.libs;
/*
 * Name: Tran Thanh Trung
 * Email: tttrung43@gmail.com
 * Date: 09/20/2013 
 * From: Sai Gon
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Connection extends SQLiteOpenHelper{
	private String DB_PATH ="";
	private String DB_NAME;	
	private Context myContext;

	public Connection(Context context, String dbName) {
		super(context, dbName, null, 1);
		this.myContext = context;
		ContextWrapper cw = new ContextWrapper(context);
		DB_PATH= cw.getDatabasePath(dbName).getParent();
		DB_NAME = dbName;
	}
	
	public Connection(Context context,String dbname, String path){
		super(context, path+dbname, null, 1);
		DB_PATH = path;
		DB_NAME =dbname;
	}

	
	public void createDatabase(){
		boolean dbExist = checkDatabase();
		if(!dbExist){
			this.getReadableDatabase();
			try{
				copyDatabase();
			}catch(Exception ex){}
		}
	}
	
	private boolean checkDatabase(){
		SQLiteDatabase checkDB = null;
		try{
			String myPath =DB_PATH+DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}catch(Exception ex){}
		if(checkDB!=null){
			checkDB.close();
			return true;
		}else return false;
	}
	
	public void copyDatabase()
	{
		try {
			InputStream myInput = myContext.getResources().getAssets().open(DB_NAME);			
			String filename=DB_PATH+DB_NAME;			
			OutputStream myOutput = new FileOutputStream(filename);
			byte[] buffer = new byte[1024];
			int length;
			while((length=myInput.read(buffer))>0){
				myOutput.write(buffer,0,length);				
			}
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (IOException e) { 
			e.printStackTrace();			
		}
		
	}
	@Override
	public void onCreate(SQLiteDatabase db) {		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
	}
}
