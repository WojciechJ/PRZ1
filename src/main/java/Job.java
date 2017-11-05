import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import org.quartz.*;

@PersistJobDataAfterExecution
public class Job  implements org.quartz.Job{

    public void execute(JobExecutionContext context) throws JobExecutionException {
        {
            String time1 = new SimpleDateFormat("HH:mm:ss").format(new Date());
            LocalTime t1 = LocalTime.parse(time1);
            String time2 = "18:45:00";
            LocalTime t2 = LocalTime.parse(time2);
            Duration diff = Duration.between(t1, t2);
            long time = (diff.toMinutes());

            if(time<=90 && time>=0) {
                System.err.println("Do konca zajęć pozostało: " + time + " minut.");
            }
            else if(time<=105 && time>=91)
            {
                time2 = "17:15:00";
                t2 = LocalTime.parse(time2);
                diff = Duration.between(t1, t2);
                time = (diff.toMinutes());
                System.err.println("Do konca przerwy pozostało: " + time + " minut.");
            }
            else if(time<=195 && time>=106) {
                time2 = "17:00:00";
                t2 = LocalTime.parse(time2);
                diff = Duration.between(t1, t2);
                time = (diff.toMinutes());
                System.err.println("Do konca zajęć pozostało: " + time + " minut.");
            }
            else if(time<=210 && time>=196)
            {
                time2 = "15:30:00";
                t2 = LocalTime.parse(time2);
                diff = Duration.between(t1, t2);
                time = (diff.toMinutes());
                System.err.println("Do konca przerwy pozostało: " + time + " minut.");
            }
            else if(time<=300 && time>=211) {
                time2 = "15:15:00";
                t2 = LocalTime.parse(time2);
                diff = Duration.between(t1, t2);
                time = (diff.toMinutes());
                System.err.println("Do konca zajęć pozostało: " + time + " minut.");
            }
            else if(time<=315 && time>=301)
            {
                time2 = "13:45:00";
                t2 = LocalTime.parse(time2);
                diff = Duration.between(t1, t2);
                time = (diff.toMinutes());
                System.err.println("Do konca przerwy pozostało: " + time + " minut.");
            }
            else if(time<=405 && time>=316) {
                time2 = "13:15:00";
                t2 = LocalTime.parse(time2);
                diff = Duration.between(t1, t2);
                time = (diff.toMinutes());
                System.err.println("Do konca zajęć pozostało: " + time + " minut.");
            }
            else if(time<=435 && time>=406)
            {
                time2 = "11:45:00";
                t2 = LocalTime.parse(time2);
                diff = Duration.between(t1, t2);
                time = (diff.toMinutes());
                System.err.println("Do konca przerwy pozostało: " + time + " minut.");
            }
            else if(time<=525 && time>=436) {
                time2 = "11:30:00";
                t2 = LocalTime.parse(time2);
                diff = Duration.between(t1, t2);
                time = (diff.toMinutes());
                System.err.println("Do konca zajęć pozostało: " + time + " minut.");
            }
            else if(time<=540 && time>=526)
            {
                time2 = "10:00:00";
                t2 = LocalTime.parse(time2);
                diff = Duration.between(t1, t2);
                time = (diff.toMinutes());
                System.err.println("Do konca przerwy pozostało: " + time + " minut.");
            }
            else if(time<=630 && time>=541) {
                time2 = "09:45:00";
                t2 = LocalTime.parse(time2);
                diff = Duration.between(t1, t2);
                time = (diff.toMinutes());
                System.err.println("Do konca zajęć pozostało: " + time + " minut.");
            }

        }
    }
}
