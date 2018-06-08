using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Quartz;
using Quartz.Impl;
using System.Collections.Specialized;

namespace GetToken
{
    
    public class MyJob : IJob
    {
        public async Task Execute(IJobExecutionContext context)
        {
            await Task.Run(() =>
            {
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(Program.url);
                request.Method = "GET";
                request.ContentType = "application/json;charset=UTF-8";
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                using (Stream myResponseStream = response.GetResponseStream())
                {
                    using (StreamReader myStreamReader = new StreamReader(myResponseStream, Encoding.UTF8))
                    {
                        Program.access_token = myStreamReader.ReadToEnd();
                        JObject job = (JObject)JsonConvert.DeserializeObject(Program.access_token);
                        string access_token = job["access_token"].ToString();
                        Console.Write(access_token);
                    }
                }
            });
            
        }
    }
    public class Program
    {
        public static string url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wwaaa6a2c1d43426a6&corpsecret=yEGNNhfGDA-HhjrZr2JROldTOd_XGnLbd5xAszV5gEc";
        public static string access_token;

        private static async Task start()
        {

            try
            {
                NameValueCollection props = new NameValueCollection
                {
                    { "quartz.serializer.type", "binary" }
                };
                StdSchedulerFactory factory = new StdSchedulerFactory(props);
                IScheduler scheduler = await factory.GetScheduler();
                await scheduler.Start();

                IJobDetail job = JobBuilder.Create<MyJob>()
                    .WithIdentity("Job1", "group1")
                    .Build();
                ITrigger trigger = TriggerBuilder.Create()
                    .WithIdentity("Trigger1", "TimeGroup1")
                    .StartNow()
                    .WithSimpleSchedule(t =>
                        t.WithIntervalInSeconds(10)
                        .RepeatForever())
                    .Build();
                await scheduler.ScheduleJob(job, trigger);
                await Task.Delay(TimeSpan.FromSeconds(60));

                //await scheduler.Shutdown();
            }
            catch (SchedulerException se)
            {
                Console.Write(se);
            }

        }

       private static void Main(string[] args)
        {
            start().GetAwaiter().GetResult();
            
            Console.ReadKey();


        }
    }
}
