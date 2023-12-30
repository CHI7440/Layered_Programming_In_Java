package com.thinking.machines.hr.bl.managers;

import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;

public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationMap;
private Map<String,DesignationInterface> titleWiseDesignationMap;
private Set<DesignationInterface> designationSet;
private static DesignationManagerInterface designationManager = null;


private DesignationManager() throws BLException
{
populateDataStructure();
}


private void populateDataStructure() throws BLException
{
this.codeWiseDesignationMap = new HashMap<>();
this.titleWiseDesignationMap = new HashMap<>();
this.designationSet = new TreeSet<>();
try
{
DesignationInterface designation;
Set<DesignationDTOInterface> dlDesignations = new DesignationDAO().getAll();
for(DesignationDTOInterface dlDesignation : dlDesignations)
{
designation = new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
codeWiseDesignationMap.put(dlDesignation.getCode(),designation);
titleWiseDesignationMap.put(dlDesignation.getTitle().toUpperCase(),designation);
designationSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException = new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}


public  static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager == null) designationManager = new DesignationManager();
return designationManager;
}


public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException = new BLException();
if(designation == null)
{
blException.setGenericException("Designation is null");
throw blException;
}
int code = designation.getCode();
String title = designation.getTitle();
if(code != 0)
{
blException.addException("Code","Code Should be zero");
}
if(title == null)
{
blException.addException("Title","Title required");
title = "";
}
else
{
title = title.trim();
if(title.length()==0) blException.addException("Title","Title Required");
}
if(title.length()>0)
{
if(this.titleWiseDesignationMap.containsKey(title.toUpperCase()))
{
blException.addException("Title","Designation "+title+" already exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
designationDAO.add(designationDTO);
code = designationDTO.getCode();
designation.setCode(code);
Designation dsDesignation;
dsDesignation = new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
codeWiseDesignationMap.put(code,dsDesignation);
titleWiseDesignationMap.put(title.toUpperCase(),dsDesignation);
designationSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}


public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException = new BLException();
if(designation == null)
{
blException.setGenericException("Designation is null");
throw blException;
}
int code = designation.getCode();
String title = designation.getTitle();
if(code <= 0)
{
blException.addException("Code","Code Should be greater than zero");
throw blException;
}
if(code>0)
{
if(!(this.codeWiseDesignationMap.containsKey(code)))
{
blException.addException("Code","Invalid Designation Code : "+code);
throw blException;
}
}
if(title == null)
{
blException.addException("Title","Title required");
title = "";
}
else
{
title = title.trim();
if(title.length()==0) blException.addException("Title","Title Required");
}
if(title.length()>0)
{
if(this.titleWiseDesignationMap.containsKey(title.toUpperCase()))
{
if(this.titleWiseDesignationMap.get(title.toUpperCase()).getCode() != code)
blException.addException("Title","Designation "+title+" exists with another code");
}
}
if(blException.hasExceptions())
{
throw blException;
}
String oldTitle = this.codeWiseDesignationMap.get(code).getTitle();
try
{
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
designationDAO.update(designationDTO);
Designation dsDesignation;
dsDesignation = new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
this.codeWiseDesignationMap.put(code,dsDesignation);
this.titleWiseDesignationMap.remove(oldTitle.toUpperCase());
this.titleWiseDesignationMap.put(title.toUpperCase(),dsDesignation);
this.designationSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}


public void removeDesignation(int code) throws BLException
{
BLException blException = new BLException();
if(code <= 0)
{
blException.addException("Code","Code Should be greater than zero");
}
if(!(this.codeWiseDesignationMap.containsKey(code)))
{
blException.addException("Code","Invalid Designation Code : "+code);
}
EmployeeManagerInterface employeeManager = EmployeeManager.getEmployeeManager();
if(employeeManager.isDesignationAlloted(code))
{
blException.addException("Code","Designation Code is Alloted to some Employee(s)");
}
if(blException.hasExceptions())
{
throw blException;
}
String title = this.codeWiseDesignationMap.get(code).getTitle();
try
{
DesignationDAOInterface designationDAO = new DesignationDAO();
designationDAO.delete(code);
this.codeWiseDesignationMap.remove(code);
this.titleWiseDesignationMap.remove(title.toUpperCase());
DesignationInterface dsDesignation = new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
this.designationSet.remove(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

DesignationInterface getDSDesignationByCode(int code)  // for internal usage inside the package
{
DesignationInterface designation;
designation = this.codeWiseDesignationMap.get(code);
return designation;
}


public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException = new BLException();
if(code <= 0)
{
blException.addException("Code","Code should be greater than zero");
throw blException;
}
if(this.codeWiseDesignationMap.containsKey(code))
{
DesignationInterface designation;
designation = this.codeWiseDesignationMap.get(code);
DesignationInterface dsDesignation = new Designation();
dsDesignation.setCode(designation.getCode());
dsDesignation.setTitle(designation.getTitle());
return dsDesignation;
}
blException.addException("Code","Code : "+code+" does not exists");
throw blException;
}


public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blException = new BLException();
if(title == null)
{
blException.addException("Title","Title required");
throw blException;
}
title = title.trim();
if(title.length() == 0)
{
blException.addException("Title","Title required");
throw blException;
}
if(this.titleWiseDesignationMap.containsKey(title.toUpperCase()))
{
DesignationInterface designation;
designation = this.titleWiseDesignationMap.get(title.toUpperCase());
DesignationInterface dsDesignation = new Designation();
dsDesignation.setCode(designation.getCode());
dsDesignation.setTitle(designation.getTitle());
return dsDesignation;
}
blException.addException("Title","Title : "+title+" does not exists");
throw blException;
}


public int getDesignationCount() throws BLException
{
return this.designationSet.size();
}


public boolean designationCodeExists(int code) throws BLException
{
return this.codeWiseDesignationMap.containsKey(code);
}


public boolean designationTitleExists(String title) throws BLException
{
if(title == null || title.length()==0) return false;
return this.titleWiseDesignationMap.containsKey(title.toUpperCase());
}


public Set<DesignationInterface> getDesignations() throws BLException
{
Set<DesignationInterface> designations;
designations = new TreeSet<>();
this.designationSet.forEach((DesignationInterface designation)->{
DesignationInterface dsDesignation = new Designation();
dsDesignation.setCode(designation.getCode());
dsDesignation.setTitle(designation.getTitle());
designations.add(dsDesignation);
});
return designations;
}
}