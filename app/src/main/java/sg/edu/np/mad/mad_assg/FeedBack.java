package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedBack extends AppCompatActivity {

    String[] problem = {"Report a Problem", "Request", "Category Request", "Suggestion", "Material Request", "Unit Request"};
    private Spinner dropdown;
    private Button submit;
    private TextInputEditText textDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_support);

        ImageView back = findViewById(R.id.backbtn);
        dropdown = findViewById(R.id.dropdown_layout);
        submit = findViewById(R.id.submitbtn);
        textDescription = findViewById(R.id.text_description);
        //FirebaseApp.initializeApp(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, problem);

        dropdown.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedBack.this, Setttings.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String emailsubject = dropdown.getText().toString();
               // String emailbody = textDescription.getText().toString();
                //sendEmail("s10247620@connect.np.edu.sg", emailsubject, emailbody);
                String emailSubject = problem[dropdown.getSelectedItemPosition()];

                String emailBody = textDescription.getText().toString();

                // Create a Firestore instance
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                // Reference to the "feedback" collection in Firestore
                CollectionReference feedbackCollection = firestore.collection("feedback");

                // Create a Feedback object and store it in Firestore
                FeedBackModel feedback = new FeedBackModel(emailSubject, emailBody);

                feedbackCollection.add(feedback)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(FeedBack.this, "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FeedBack.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FeedBack.this, "Failed to send feedback. Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    /*public static void sendEmail(String receiver,String title,String message){
        AWSCredentials credentials = getCreds();
        AmazonSimpleEmailServiceClient sesClient = new AmazonSimpleEmailServiceClient( credentials );

        Content subjectContent = new Content(title);
        Body messageBody = new Body(new Content(message));
        Message feedbackMessage = new Message(subjectContent,messageBody);
        Destination destination = new Destination().withToAddresses(receiver);

        SendEmailRequest request = new SendEmailRequest(receiver,destination,feedbackMessage);
        SendEmailResult result =  sesClient.sendEmail(request);
    }*/
}



