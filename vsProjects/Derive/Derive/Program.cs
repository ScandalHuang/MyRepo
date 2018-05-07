using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Oracle.ManagedDataAccess.Client;
using Oracle.ManagedDataAccess.Types;
using System.Collections;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

//在References中右键Add references->移到.net tab->勾选System.Data项然后点击ok,添加对System.data的引用,否则运行时会抛出system.data.common.dbconnectiony未定义异常.
using System.Data;


namespace OracleConnetion
{
    public class Record
    {
        public string CARD_ID { get; set; }
        public string PDATE { get; set; }
        public string PFIRST { get; set; }
        public string PLAST { get; set; }
        public string EMPWNO { get; set; }
        public string EMPCNM { get; set; }
        public string DEPNAM { get; set; }
        public string DEPTCNM { get; set; }
    }
    class oracleconnection
    {
        static void Main(string[] args)
        {
//            //创建Oracle连接并打开
//            string strconn = @"Data Source = (DESCRIPTION = (ADDRESS = 
//            (PROTOCOL = TCP)(HOST = 10.214.5.47)(PORT = 1521))(CONNECT_DATA = (SERVICE_NAME = zcvdw)));" +
//            "User Id =PEOPLESEARCH;Password =sdpmis;";
//            OracleConnection dbConn = new OracleConnection(strconn);
//            dbConn.Open();

//            //创建OracleCommand对象
//            OracleCommand cmd = new OracleCommand(
//            "select * from TB_RECORD", dbConn);
//            OracleDataReader odr = cmd.ExecuteReader();
//            string str = "";
//            while (odr.Read())
//            {
//                str += "CARD_ID:"+odr["CARD_ID"].ToString()+"\n";
//                str += "PDATE:" + odr["PDATE"].ToString() + "\n";
//                str += "PFIRST:" + odr["PFIRST"].ToString() + "\n";
//                str += "PLAST:" + odr["PLAST"].ToString() + "\n";
//                str += "EMPWNO:" + odr["EMPWNO"].ToString() + "\n";
//                str += "EMPCNM:" + odr["EMPCNM"].ToString() + "\n";
//                str += "DEPNAM:" + odr["DEPNAM"].ToString() + "\n";
//                str += "CARD_ID:" + odr["CARD_ID"].ToString() + "\n";
//            }
//            Console.WriteLine(str);
//            Console.Read();
//            dbConn.Close();
            ArrayList records = new ArrayList();
            string strconn = @"Data Source = (DESCRIPTION = (ADDRESS = 
            (PROTOCOL = TCP)(HOST = 10.214.5.47)(PORT = 1521))(CONNECT_DATA = (SERVICE_NAME = zcvdw)));" +
            "User Id =PEOPLESEARCH;Password =sdpmis;";
            string table = "TB_RECORD";
            string str = "";
            string cmdstr = String.Format("select * from {0}", table);
            using (OracleConnection dbconn = new OracleConnection(strconn))
            {
                using (OracleCommand cmd = new OracleCommand(cmdstr, dbconn))
                {
                    cmd.Connection.Open();
                    using (OracleDataReader dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                        {
                            Record record = new Record();
                            record.CARD_ID = dr["CARD_ID"].ToString();
                            record.PDATE = dr["PDATE"].ToString();
                            record.PFIRST = dr["PFIRST"].ToString();
                            record.PLAST = dr["PLAST"].ToString();
                            record.EMPWNO = dr["EMPWNO"].ToString();
                            record.EMPCNM = dr["EMPCNM"].ToString();
                            record.DEPNAM = dr["DEPNAM"].ToString();
                            record.DEPTCNM = dr["DEPTCNM"].ToString();
                            records.Add(record);
                        }
                    }
                }
            }
            //JObject j = (JObject)JsonConvert.SerializeObject(records);
            //str = j.ToString();
            var j = JArray.FromObject(records);
            str = j.ToString();
            Console.Write(str);
            Console.Read();
        }
    }
}