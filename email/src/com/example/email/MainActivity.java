package com.example.email;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity  extends Activity 
{
	private static final int SELECT_PICTURE = 1;
    Button send;
    EditText address, subject, emailtext ,emailcc;
	private TextView txt;
	private String selectedImagePath = null;
	String ss;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.txt);
        send=(Button) findViewById(R.id.emailsendbutton);
        address=(EditText) findViewById(R.id.emailaddress);
        subject=(EditText) findViewById(R.id.emailsubject);
        emailtext=(EditText) findViewById(R.id.emailtext);
      //  emailcc=(EditText) findViewById(R.id.emailcc);

        //Intent myintent = getIntent();
       // String ReqPath = myintent.getStringExtra("filepath");
       // File fileIn = new File(ReqPath);
       // final Uri u = Uri.fromFile(fileIn);
        
        txt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent();
                 intent.setType("image/*");
                 intent.setAction(Intent.ACTION_GET_CONTENT);

                 startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
			}
		});

        send.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
            	
            	File fileIn = new File(ss);
            	final Uri u = Uri.fromFile(fileIn);

                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                emailIntent.setType("plain/text");

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ address.getText().toString()});

               // emailIntent.putExtra(android.content.Intent.EXTRA_CC,  new String[]{emailcc.getText().toString()});

                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject.getText());

                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext.getText());

                emailIntent.putExtra(Intent.EXTRA_STREAM, u);

                MainActivity.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                //finish();
            }
        });
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                //img.setVisibility(0);
                //img.setImageURI(selectedImageUri);
                ss = selectedImagePath.replace("/mnt", "");
                
                Toast.makeText(getApplicationContext(), ss, Toast.LENGTH_LONG).show();
            }
        }
    }
    
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}