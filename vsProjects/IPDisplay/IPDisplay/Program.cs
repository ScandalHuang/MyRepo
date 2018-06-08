using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace IPDisplay
{
    class Program
    {
        static void Main(string[] args)
        {
            string name = Dns.GetHostName();
            IPAddress[] ipadrlist = Dns.GetHostAddresses(name);
            foreach (IPAddress ipa in ipadrlist)
            {
                    Console.WriteLine(ipa.ToString());
            }  
            Console.Read();
        }
    }
}
