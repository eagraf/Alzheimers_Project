package graf.ethan.alzheimers_project;

/**
 * Created by Ethan on 7/22/2015.
 */
public interface NotificationEngine {
    //Notify caretaker through sms.
    public void notifySMS(PatientState patientState, EnvironmentState environmentState);
    //Notify caretaker through email.
    public void notifyEmail(PatientState patientState, EnvironmentState environmentState);
    //Notify caretaker through the app.
    public void notifyApp(PatientState patientState, EnvironmentState environmentState);
}
