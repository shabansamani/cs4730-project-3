
/*****************************************************************************************
 * @file  TestTupleGenerator.java
 *
 * @author   Sadiq Charaniya, John Miller
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.out;

/*****************************************************************************************
 * This class tests the TupleGenerator on the Student Registration Database defined in the
 * Kifer, Bernstein and Lewis 2006 database textbook (see figure 3.6).  The primary keys
 * (see figure 3.6) and foreign keys (see example 3.2.2) are as given in the textbook.
 */
public class TestTupleGenerator
{
    /*************************************************************************************
     * The main method is the driver for TestGenerator.
     * @param args  the command-line arguments
     */
    public static void main (String [] args)
    {
        TupleGenerator test = new TupleGeneratorImpl ();
        // TupleGenerator selectRange = new TupleGeneratorImpl() ;


        test.addRelSchema ("Student",
                "id name address status",
                "Integer String String String",
                "id",
                null);

        test.addRelSchema ("Professor",
                "id name deptId",
                "Integer String String",
                "id",
                null);

        test.addRelSchema ("Course",
                "crsCode deptId crsName descr",
                "String String String String",
                "crsCode",
                null);

        test.addRelSchema ("Teaching",
                "crsCode semester profId",
                "String String Integer",
                "crcCode semester",
                new String [][] {{ "profId", "Professor", "id" },
                        { "crsCode", "Course", "crsCode" }});

        test.addRelSchema ("Transcript",
                "studId crsCode semester grade",
                "Integer String String String",
                "studId crsCode semester",
                new String [][] {{ "studId", "Student", "id"},
                        { "crsCode", "Course", "crsCode" },
                        { "crsCode semester", "Teaching", "crsCode semester" }});




        //Student Table
        String [] tableStudent = { "Student"};
        int size = 10000;
        int tup[] = new int[]{size};
        Comparable[][][] resultTestSelect = test.generate(tup);
        
        
        Table studentTable = new Table("Student_Table", "id name address status", "Integer String String String", "id");
        for (int i = 0; i < resultTestSelect.length; i++) {
            for (int j = 0; j < resultTestSelect[i].length; j++) {
                studentTable.insert(resultTestSelect[i][j]);
            } // for
        } // for
        

        out.println( "Student Table: " + " Number of tuples :" + size);
        out.println();
        Long time_start, time_end, time_taken;

        
        //Query 1 Before Tuning
        out.println("Selected row: " + resultTestSelect[0][5000][0]);
        int selectedStudentId = (int) resultTestSelect[0][5000][0];
        time_start = System.currentTimeMillis();
        Table t_select1 = studentTable.select(t -> (Integer) t[studentTable.col("id")] == selectedStudentId).project("name");
        time_end = System.currentTimeMillis();
        time_taken = time_end - time_start;
        System.out.println("Query 1 before tuning takes: " + time_taken + " msecs.");
        
        //Query 2 Before Tuning
        time_start = System.currentTimeMillis();
        Table t_select2 = studentTable.select(t -> (Integer) t[studentTable.col("id")] >= 2000 && (Integer) t[studentTable.col("id")] <= 20000).project("name");
        time_end = System.currentTimeMillis();
        time_taken = time_end - time_start;
        System.out.println("Query 2 before tuning takes: " + time_taken + " msecs.");

/*        int[] size = new int[] {10000,50000,100000,200000,300000,400000};

        for(int s = 0; s < size.length; s++) {

            int tup[] = new int[]{size[s]};

            Comparable[][][] resultTestSelect = test.generate(tup);
            Table studentTable = new Table("Student_Table", "id name address status", "Integer String String String", "id");

            //    Table StudentTable100, StudentTable200, StudentTable500, StudentTable1000, StudentTable2000, StudentTable5000, StudentTable10000, StudentTable50000;


            for (int i = 0; i < resultTestSelect.length; i++) {
                for (int j = 0; j < resultTestSelect[i].length; j++) {
                    studentTable.insert(resultTestSelect[0][j]);
                } // for
            } // for


            //--------------------- select: <

            out.println( "Point "+ (s+1) + " Number of tuples :" + size[s]);
            out.println();
            for (int i = 0; i < 13; i++) {
                Long time_start = System.currentTimeMillis();
                Table t_select2 = studentTable.select(t -> (Integer) t[studentTable.col("id")] >= 2000 && (Integer) t[studentTable.col("id")] <= 20000);

                Long time_end = System.currentTimeMillis();
                Long time_taken = time_end - time_start;
                //t_select2.print ();
                //t_select2.printIndex ();

                //System.out.println("Time taken for the select query: " +i+ "th time is " + time_taken);
                System.out.println("Sample " + (i+1) + " takes " + time_taken + " msecs.");
            }//for
        }//for
*/

        
        //Query 3
        
        //Course Table
        String [] tableCourse = { "Course"};
        int sizeofCourses = 2000;
        int tupC[] = new int[]{sizeofCourses};
        Comparable[][][] resultofCourseSelect = test.generate(tupC);
        
        
        Table courseTable = new Table("Course_Table", "crsCode deptId crsName descry", "Integer Integer String String", "crsCode");
        for (int i = 0; i < resultofCourseSelect.length; i++) {
            for (int j = 0; j < resultofCourseSelect[i].length; j++) {
                courseTable.insert(resultofCourseSelect[i][j]);
            } // for
        } // for
        
        out.println( "Course Table: " + " Number of tuples :" + sizeofCourses);
        out.println();
       


    } // main

} // TestTupleGenerator
