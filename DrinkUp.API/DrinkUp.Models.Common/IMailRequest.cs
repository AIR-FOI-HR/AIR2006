using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IMailRequest
    {
        string To { get; set; }
        string Subject { get; set; }
        string Body { get; set; }
    }
}
