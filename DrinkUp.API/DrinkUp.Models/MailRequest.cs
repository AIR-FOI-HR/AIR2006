using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class MailRequest : IMailRequest
    {
        public string To { get; set; }
        public string Subject { get; set; }
        public string Body { get; set; }
    }
}
