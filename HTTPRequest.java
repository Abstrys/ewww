//
//                 Ewww Web Server -- A simple Java web-server.
//                     Copyright (C) 2005, Eron J. Hennessey
// 
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by the Free
// Software Foundation; either version 2 of the License, or (at your option)
// any later version.
// 
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
// more details.
// 
// You should have received a copy of the GNU General Public License along with
// this program; if not, write to the Free Software Foundation, Inc., 51
// Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//

import java.io.*;
import java.net.Socket;

/**
 * A class that implements a thread whose specific role is to handle an HTTP
 * request.
 * @author Eron Hennessey
 */
public class HTTPRequest extends Thread
{
    private Socket con_socket;

    public HTTPRequest(Socket s)
    {
        con_socket = s;
    }

    /**
     * The main procedure for this class.
     */
    public void run()
    {
        try
        {
            BufferedReader sock_in = new BufferedReader(
                new InputStreamReader(con_socket.getInputStream()));
            PrintWriter sock_out = new PrintWriter(con_socket.getOutputStream());

            // read the first line of the request.  This should be a valid HTTP command, like GET.
            String input_line = sock_in.readLine();
            String tokens[] = input_line.split("\\s");

            // check to see if it's a valid HTTP command.
            if(tokens[0].equals("GET"))
            {
                processGETRequest(tokens, sock_in, sock_out);
            }
            else // not a valid HTTP command
            {
                sock_out.println("HTTP/1.0 400 Bad Request");
                sock_out.println("");
            }

            sock_out.flush();
            con_socket.close();
        }
        catch(IOException exc)
        {
            System.err.println("IO Exception caught in HTTPRequest!");
            System.err.println("Thread exiting...");
            return;
        }
    }

    /**
     * Handles reading an HTTP GET request.
     */
    private void processGETRequest(String[] tokens, BufferedReader sock_in, PrintWriter sock_out)
    {
        // what file does the client want?
        String desired_file = Ewww.document_root + tokens[1];

        File file = new File(desired_file);

        // if the file is a directory, look for the index.html file.
        if(file.isDirectory())
        {
            desired_file = desired_file + "index.html";
            file = new File(desired_file);
        }

        BufferedReader file_in = null;
        try
        {
            file_in = new BufferedReader(new FileReader(file));
        }
        catch(FileNotFoundException exc)
        {
            sock_out.println("HTTP/1.0 404 Not Found");
            sock_out.println("");
            return;
        }

        // the file exists... send it!

        sock_out.println("HTTP/1.0 200 OK");
        sock_out.println("Content-Type: text/html");
        sock_out.println("Content-Length: " + file.length());
        sock_out.println("");

        try
        {
            while(true)
            {
                String line = file_in.readLine();
                if(line == null)
                {
                    break;
                }
                sock_out.println(line);
            }
        }
        catch(IOException exc)
        {
            return;
        }
    }
}
