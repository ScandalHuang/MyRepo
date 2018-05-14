using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using System.Configuration;
using System.Web.Cors;
using System.Web.Http.Cors;

namespace MyWeb
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            // Web API configuration and services
            config.EnableCors(new System.Web.Http.Cors.EnableCorsAttribute("http://localhost:3000", "*", "*"));
            //var allowedMethods = ConfigurationManager.AppSettings["cors:allowedMethods"];
            //var allowedOrigin = ConfigurationManager.AppSettings["cors:allowedOrigin"];
            //var allowedHeaders = ConfigurationManager.AppSettings["cors:allowedHeaders"];
            //var geduCors = new EnableCorsAttribute(allowedHeaders, allowedMethods, allowedOrigin)
            //{
            //    SupportsCredentials = true
            //};
            //config.EnableCors(geduCors);
            // Web API routes
            config.MapHttpAttributeRoutes();

            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );
        }
    }
}
