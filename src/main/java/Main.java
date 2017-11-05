import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        try {

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail job1 = newJob(Job.class)
                    .withIdentity("p1", "Lessons")
                    .build();
            JobDetail job2 = newJob(Job.class)
                    .withIdentity("p2", "Lessons")
                    .build();
            JobDetail job3 = newJob(Job.class)
                    .withIdentity("p3", "Lessons")
                    .build();


            //academic year
            String startDateStr = "2017-10-01 00:00:00.0";
            String endDateStr = "2018-06-29 00:00:00.0";

            Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(startDateStr);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(endDateStr);

            //8:15-8:59
            CronTrigger trigger1 = newTrigger()
                    .withIdentity("part1", "Lessons")
                    .startAt(startDate)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 15-59/3 8 * * ? *").withMisfireHandlingInstructionDoNothing())
                    .endAt(endDate)
                    .forJob("p1", "Lessons")
                    .build();
            //9:00-18:00
            CronTrigger trigger2 = newTrigger()
                    .withIdentity("part2", "Lessons")
                    .startAt(startDate)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0-59/3 9-17 * * ? *").withMisfireHandlingInstructionDoNothing())
                    .endAt(endDate)
                    .forJob("p2", "Lessons")
                    .build();
            //18:00-18:45
            CronTrigger trigger3 = newTrigger()
                    .withIdentity("part3", "Lessons")
                    .startAt(startDate)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0-45/3 18 * * ? *").withMisfireHandlingInstructionDoNothing())
                    .endAt(endDate)
                    .forJob("p3", "Lessons")
                    .build();

            //schedule jobs
            scheduler.scheduleJob(job1, trigger1);
            scheduler.scheduleJob(job2, trigger2);
            scheduler.scheduleJob(job3, trigger3);

            //start
            scheduler.start();


        } catch (SchedulerException se) {
            se.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //SQL sequence input
        Scanner userInput = new Scanner( System.in );
        String sqlQuery;
        int taskNumber;
        System.out.print("Enter task number: ");
        taskNumber = userInput.nextInt( );
        userInput.nextLine();
        System.out.printf("Enter your SQL query: ");
        sqlQuery = userInput.nextLine( );
        System.out.println("Your task number: "+taskNumber);
        System.out.println("Your query: "+sqlQuery);
    }
}
