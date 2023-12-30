package com.thinking.machines.network.client;

import com.thinking.machines.network.common.*;
import com.thinking.machines.network.exceptions.*;
import java.net.*;
import java.io.*;

public class NetworkClient
{
public Response send(Request request) throws NetworkException
{
try
{
ObjectOutputStream oos;
ByteArrayOutputStream baos;
baos = new ByteArrayOutputStream();
oos = new ObjectOutputStream(baos);
oos.writeObject(request);
oos.flush();
byte objectBytes[];
objectBytes = baos.toByteArray();
int requestLength = objectBytes.length;
byte header[] = new byte[1024];
int i,x;
x = requestLength;
i = 1023;
while(x>0)
{
header[i] = (byte)(x%10);
x = x/10;
i--;
}
Socket socket = new Socket("localhost",5500);
OutputStream os = socket.getOutputStream();
os.write(header,0,1024);
os.flush();
byte ack[] = new byte[1];
InputStream is = socket.getInputStream();
int bytesCount = 0;
while(true)
{
bytesCount = is.read(ack);
if(bytesCount==-1) continue;
break;
}
int bytesToSend = requestLength;
i=0;
int chunkSize = 1024;
while(i<bytesToSend)
{
if((bytesToSend-i)<1024) chunkSize = bytesToSend-i;
os.write(objectBytes,i,chunkSize);
os.flush();
i+=chunkSize;
}
int bytesToRecieve = 1024;
header = new byte[1024];
i=0;
int k,j=0;
byte tmp[] = new byte[1024];
while(j<bytesToRecieve)
{
bytesCount = is.read(tmp);
if(bytesCount==-1) continue;
for(k=0;k<bytesCount;k++)
{
header[i] = tmp[k];
i++;
}
j += bytesCount;
}
j=1023;
i=1;
int responseLength=0;
while(j>=0)
{
responseLength = responseLength + (header[j]*i);
i = i*10;
j--;
}
ack[0] = 1;
os.write(ack,0,1);
os.flush();
j=0;
i=0;
byte response[] = new byte[responseLength];
bytesToRecieve = responseLength;
while(i<bytesToRecieve)
{
bytesCount = is.read(tmp);
if(bytesCount==-1) continue;
for(k=0;k<bytesCount;k++)
{
response[i] = tmp[k];
i++;
}
j+=bytesCount;
}
System.out.println("Response Recieved");
ack[0]=1;
os.write(ack,0,1);
os.flush();
socket.close();
ByteArrayInputStream bais = new ByteArrayInputStream(response);
ObjectInputStream ois = new ObjectInputStream(bais);
Response responseObject = (Response)ois.readObject();
return responseObject;
}catch(Exception e)
{
throw new NetworkException(e.getMessage());
}
}
}

