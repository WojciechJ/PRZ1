import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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


            //rok akademicki
            String startDateStr = "2017-10-01 00:00:00.0";
            String endDateStr = "2018-06-29 00:00:00.0";

            Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(startDateStr);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(endDateStr);

            //rozklad czasowy na WMI
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

            scheduler.scheduleJob(job1, trigger1);
            scheduler.scheduleJob(job2, trigger2);
            scheduler.scheduleJob(job3, trigger3);
            scheduler.start();

        } catch (SchedulerException se) {
            se.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //tworzenie pliku txt
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("answers.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Podanie danych
        Scanner userInput = new Scanner( System.in );
        String sqlQuery;
        String taskNumber;
        List<String> correctAnswers = new ArrayList<>();

        for(;;)
        {
            System.out.print("Podaj numer zadania: ");
            taskNumber = userInput.nextLine();
            System.out.println("Podaj zapytanie SQL: ");
            sqlQuery = userInput.nextLine();

            //sprawdzanie poprawności zapytania
            String[] arr = sqlQuery.split(" ");
            Boolean select = false, from = false, where = false, group = false, having = false, order = false, correct = true;
            for (String ss : arr) {
                if (Objects.equals(ss, "select")) {
                    if (from == false && where == false && group == false && having == false && order == false) {
                        select = true;
                    } else {
                        correct = false;
                        break;
                    }
                } else if (Objects.equals(ss, "from")) {
                    if (select)
                        from = true;
                    else {
                        correct = false;
                        break;
                    }
                } else if (Objects.equals(ss, "where")) {
                    if (select && from)
                        where = true;
                    else {
                        correct = false;
                        break;
                    }

                } else if (Objects.equals(ss, "group")) {
                    if (select && from && where)
                        group = true;
                    else {
                        correct = false;
                        break;
                    }
                } else if (Objects.equals(ss, "having")) {
                    if (select && from && where && group)
                        having = true;
                    else {
                        correct = false;
                        break;
                    }
                } else if (Objects.equals(ss, "order")) {
                    if (select && from && where && group && having)
                        order = true;
                    else {
                        correct = false;
                        break;
                    }
                }
            }
            if(!select)
                correct=false;
            //odpowiedz na to samo zadanie nadpisuje je
            if (correct)
            {
                if(!correctAnswers.isEmpty())
                {
                    Boolean contains=false;
                    for (int i = 0; i < correctAnswers.size(); i++)
                    {
                        if (correctAnswers.get(i).contains(taskNumber))
                        {
                            String tmp=correctAnswers.get(i).replace(correctAnswers.get(i), taskNumber+" "+sqlQuery);
                            correctAnswers.set(i, tmp);
                            System.out.println("Twoje zapytanie jest poprawne. Zadanie zostało nadpisane.");
                            contains=true;
                            break;
                        }
                    }
                    if(!contains)
                    {
                        System.out.println("Twoje zapytanie jest poprawne.");
                        correctAnswers.add(taskNumber + " " + sqlQuery);
                    }
                }
                else
                {
                    System.out.println("Twoje zapytanie jest poprawne.");
                    correctAnswers.add(taskNumber + " " + sqlQuery);
                }

            }
            else
            {
                System.out.println("BLĄD: Twoje zapytanie jest niepoprawne.");
            }

            //reczne zapisanie posortowanych odpowiedzi
            System.out.println("Kontynuuj [k] Zakończ [z]");
            String cont = userInput.nextLine();
            if(Objects.equals(cont, "z"))
            {
                java.util.Collections.sort(correctAnswers);
                for(int i=0;i<correctAnswers.size();i++)
                {
                    writer.println(correctAnswers.get(i));
                }
                writer.close();
                break;
            }


        }


    }
}
