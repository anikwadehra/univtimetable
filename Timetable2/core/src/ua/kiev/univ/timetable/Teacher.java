package ua.kiev.univ.timetable;

//---this class used only for storing information about teachers
//---and doesn't used in genetic algoritm
public class Teacher {
    private static Integer[] all_idTeacher = new Integer[Start.MAX_TEACHERS];
    private static String[] all_nameTeacher = new String[Start.MAX_TEACHERS];
    
    Teacher(){
      
    }

    protected static void setIdTeacher(Integer a_idTeacher, Integer a_index) {
        Teacher.all_idTeacher[a_index] = a_idTeacher;
    }

    protected static void setNameTeacher(String a_nameTeacher, Integer a_index) {
        Teacher.all_nameTeacher[a_index] = a_nameTeacher;
    }

    protected static String[] getNameTeachers(Integer[] a_idTeachers) {
        String[] nameTeachers = new String[a_idTeachers.length];
        for (int i = 0; i < a_idTeachers.length; i++) {
            for (int j = 0; j < all_idTeacher.length; j++) {
                if( a_idTeachers[i] == all_idTeacher[j] )
                    nameTeachers[i] = all_nameTeacher[j];
            }
        }
        return nameTeachers;
    }
}
