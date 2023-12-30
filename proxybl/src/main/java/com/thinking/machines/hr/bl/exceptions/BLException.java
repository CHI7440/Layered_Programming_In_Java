package com.thinking.machines.hr.bl.exceptions;

import java.util.*;

public class BLException extends Exception
{
private String genericException;
private Map<String,String> propertiesException;
public BLException()
{
genericException = null;
propertiesException = new HashMap<>();
}
public void setGenericException(String genericException)
{
this.genericException = genericException;
}
public String getGenericException()
{
if(genericException == null) return "";
return this.genericException;
}
public void addException(String property, String exception)
{
this.propertiesException.put(property,exception);
}
public String getException(String property)
{
return this.propertiesException.get(property);
}
public void removeException(String property)
{
this.propertiesException.remove(property);
}
public int getExceptionsCount()
{
if(this.genericException != null) return this.propertiesException.size()+1;
return this.propertiesException.size();
}
public boolean hasException(String property)
{
return this.propertiesException.containsKey(property);
}
public boolean hasGenericException()
{
return this.genericException != null;
}
public boolean hasExceptions()
{
return this.getExceptionsCount() > 0;
}
public List<String> getProperties()
{
List<String> properties = new ArrayList<>();
this.propertiesException.forEach((k,v)->{
properties.add(k);
});
return properties;
}
public String getMessage()
{
if(this.genericException == null) return "";
return this.genericException;
}
}
