using DrinkUp.Models;
using DrinkUp.Models.Common;
using DrinkUp.Service.Common;
using MailKit.Net.Smtp;
using MailKit.Security;
using Microsoft.Extensions.Options;
using MimeKit;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service
{
    public class MailService : IMailService
    {
        private readonly IMailSettings mailSettings;

        public MailService(IOptions<MailSettings> mailSettings)
        {
            this.mailSettings = mailSettings.Value;
        }

        public async Task SendEmailAsync(IMailRequest mailRequest)
        {
            MimeMessage email = new MimeMessage
            {
                Sender = MailboxAddress.Parse(mailSettings.Mail)
            };
            email.From.Clear();
            email.From.Add(MailboxAddress.Parse(mailSettings.From));
            email.To.Add(MailboxAddress.Parse(mailRequest.To));
            email.Subject = mailRequest.Subject;

            BodyBuilder builder = new BodyBuilder
            {
                HtmlBody = mailRequest.Body
            };            
            email.Body = builder.ToMessageBody();

            using MailKit.Net.Smtp.SmtpClient smtp = new MailKit.Net.Smtp.SmtpClient();
            smtp.Connect(mailSettings.Host, mailSettings.Port, SecureSocketOptions.StartTls);
            smtp.Authenticate(mailSettings.Mail, mailSettings.Password);
            await smtp.SendAsync(email);
            smtp.Disconnect(true);
        }
    }
}
