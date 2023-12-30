import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.io.*;

class DesignationManagerGetDesignationsTestCase
{
public static void main(String gg[])
{
Set<DesignationInterface> designations;
try
{
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
designations = designationManager.getDesignations();
designations.forEach((DesignationInterface designation)->{
System.out.println(designation.getCode()+" : "+designation.getTitle());
});
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties = blException.getProperties();
for(String property : properties)
{
System.out.println(blException.getException(property));
}
}
}
}
