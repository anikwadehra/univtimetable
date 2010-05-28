package ua.kiev.univ.timetable;

//---this class used only for storing information about groups
//---and doesn't used in genetic algoritm
public class Group {
    private static Integer[] all_idGroup   = new Integer[Start.MAX_GROUPS];
    private static Integer[] all_groupSize = new Integer[Start.MAX_GROUPS];
    private static String[]  all_nameGroup = new String[Start.MAX_GROUPS];
    
    Group(){
      
    }

    protected static void setIdGroup(Integer a_idGroup, Integer a_index) {
        Group.all_idGroup[a_index] = a_idGroup;
    }

    protected static void setGroupSize(Integer a_groupSize, Integer a_index) {
        Group.all_groupSize[a_index] = a_groupSize;
    }

    protected static void setNameGroup(String a_nameGroup, Integer a_index) {
        Group.all_nameGroup[a_index] = a_nameGroup;
    }

    protected static Integer[] getGroupsSize(Integer[] a_idGroups) {
      Integer[] groupsSize = new Integer[a_idGroups.length];
      for (int i = 0; i < a_idGroups.length; i++) {
        for (int j = 0; j < all_idGroup.length; j++) {
                if(a_idGroups[i] == all_idGroup[j])
                    groupsSize[i] = all_groupSize[j];
            }
        }
        return groupsSize;
    }

    protected static String[] getNameGroups(Integer[] a_idGroups) {
      Integer index = 0;
      String[] nameGroups = new String[a_idGroups.length];
      for (int i = 0; i < a_idGroups.length; i++) {
        for (int j = 0; j < all_idGroup.length; j++) {
                if(a_idGroups[i] == all_idGroup[j])
                    nameGroups[i] = all_nameGroup[j];
            }
        }
        return nameGroups;
    }
}
