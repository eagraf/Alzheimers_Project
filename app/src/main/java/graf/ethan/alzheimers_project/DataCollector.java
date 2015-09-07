package graf.ethan.alzheimers_project;

/**
 * Created by Ethan on 7/22/2015.
 */
public interface DataCollector {
    //Register a new patient that data will be collected from.
    public void registerPatient(Patient patient);
    //Collect the current state of a patient.
    public void collectPatientState(PatientState patientState, EnvironmentState environmentState);
}
