using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IMailSettings
    {
        string Mail { get; set; }
        string From { get; set; }
        string DisplayName { get; set; }
        string Password { get; set; }
        string Host { get; set; }
        int Port { get; set; }
    }
}
