﻿using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface IMailService
    {
        Task SendEmailAsync(IMailRequest mailRequest);
    }
}