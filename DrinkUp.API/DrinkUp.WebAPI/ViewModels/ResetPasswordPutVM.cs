using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.Controllers
{
    public class ResetPasswordPutVM
    {
        public string Password { get; set; }
        public string Password2 { get; set; }
        public string Email { get; set; }
        public string Token { get; set; }
    }
}
