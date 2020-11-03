using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Server.IIS.Core;
using Microsoft.Extensions.Logging;
using DrinkUp.DAL.Entities;
using System.Net.Http;
using DrinkUp.DAL;

namespace DrinkUp.WebAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class WeatherForecastController : ControllerBase
    {
        [HttpGet]
        [Route("db")]
        public string Info()
        {
            return new Test().Get();
        }
    }
}
