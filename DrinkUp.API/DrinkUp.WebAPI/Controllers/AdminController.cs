using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Net.Http;

namespace DrinkUp.WebAPI.Controllers
{
    [Route("api/[controller]")]
    public class AdminController : Controller
    {
        [HttpGet("")]
        public IActionResult Admin()
        {
            return Unauthorized();
        }
    }
}
