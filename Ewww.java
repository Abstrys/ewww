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

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/**
 * A very, very simple web server in Java
 * @author Eron Hennessey
 */
public class Ewww
{
    private static final String SVR_NAME = "Ewww Web Server";
    private static final String SVR_VER = "0.1";

    public static String document_root = "htdocs";

    /**
     * The main method.  Simply sets up the connection and begins listening to requests, launching
     * a new thread each time a request is received.
     */
    public static void main(String[] args)
    {
        int listen_port = 80;
        int max_requests = 9;

        for(int i = 0; i < args.length; i++)
        {
            if(args[i].charAt(0) != '-')
            {
                System.err.println("Unknown argument: \"" + args[i] + "\"\n");
                showUsage();
                return;
            }

            switch(args[i].charAt(1))
            {
            case 'p':
                listen_port = Integer.parseInt(args[++i]);
                break;

            case 'r':
                max_requests = Integer.parseInt(args[++i]);
                break;

            case 'd':
                document_root = args[++i];
                break;

            case 'v':
                System.out.println(SVR_NAME);
                System.out.println("Version " + SVR_VER);
                return;

            default:
                System.err.println("Unknown argument: \"" + args[i] + "\"\n");
                // intentional fallthrough here...
            case '?':
            case 'h':
                showUsage();
                return;
            }
        }

        // set up the server
        try
        {
            ServerSocket server = new ServerSocket(listen_port, max_requests);
            System.out.println(SVR_NAME + " started.");
            System.out.println("Listening on " + server.getInetAddress() + ":" + server.getLocalPort());
            System.out.println("Waiting for clients to connect...");

            // the main loop
            while(true)
            {
                Socket s = server.accept();
                HTTPRequest h = new HTTPRequest(s);
                h.start();
            }
        }
        catch(IOException exc)
        {
            System.err.println("IO Exception caught!  Exiting...");
            System.err.println(exc.getLocalizedMessage());
            exc.printStackTrace();
        }
    }

    public static void showUsage()
    {
        System.out.println("usage:");
        System.out.println("  java Ewww [args]");
        System.out.println("\narguments:");
        System.out.println("  -?,-h          - Show this text.");
        System.out.println("  -v             - Show version.");
        System.out.println("  -d <dir>       - The directory to serve pages from.");
        System.out.println("  -p <portnum>   - The port to listen on.  Default is 80.");
        System.out.println("  -r <requests>  - Maximum requests that can be handled at once. Default is 9.");
    }
}
