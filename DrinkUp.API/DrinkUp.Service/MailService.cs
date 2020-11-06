using DrinkUp.Models;
using DrinkUp.Models.Common;
using DrinkUp.Service.Common;
using MailKit.Net.Smtp;
using MailKit.Security;
using Microsoft.Extensions.Options;
using MimeKit;
using System;
using System.Collections.Generic;
using System.IO;
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

        public IMailRequest CreateRegistrationMail(string email, string name, string token)
        {
            string FilePath = Directory.GetCurrentDirectory() + "\\wwwroot\\Templates\\AccountActivation.html";
            StreamReader str = new StreamReader(FilePath);
            string MailText = str.ReadToEnd();
            str.Close();
            MailText = MailText.Replace("[username]", name).Replace("[token]", token);
            return new MailRequest()
            {
                To = email,
                Subject = "Activate your DrinkUp account",
                Body = MailText
            };
        }

        public IMailRequest CreatePasswordResetEmail(string email, string name, string token)
        {
            string FilePath = Directory.GetCurrentDirectory() + "\\wwwroot\\Templates\\PasswordResetMail.html";
            StreamReader str = new StreamReader(FilePath);
            string MailText = str.ReadToEnd();
            str.Close();
            MailText = MailText.Replace("[username]", name).Replace("[token]", token).Replace("[email]", email);
            return new MailRequest()
            {
                To = email,
                Subject = "Reset your DrinkUp password",
                Body = MailText
            };
        }

        
    }
}
