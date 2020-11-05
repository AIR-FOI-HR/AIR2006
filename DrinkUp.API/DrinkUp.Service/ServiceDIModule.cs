using Autofac;
using DrinkUp.Models.Common;
using DrinkUp.Service.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Service
{
    public class ServiceDIModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder.RegisterType<KorisnikService>().As<IKorisnikService>();
            builder.RegisterType<KorisnikTokenService>().As<IKorisnikTokenService>();
            builder.RegisterType<ObjektPonudaService>().As<IObjektPonudaService>();
            builder.RegisterType<ObjektService>().As<IObjektService>();
            builder.RegisterType<PonudaService>().As<IPonudaService>();
            builder.RegisterType<TokenService>().As<ITokenService>();
            builder.RegisterType<UlogaService>().As<IUlogaService>();
            builder.RegisterType<VrstaPonudeService>().As<IVrstaPonudeService>();
            builder.RegisterType<ZaposlenikObjektService>().As<IZaposlenikObjektService>();
            builder.RegisterType<MailService>().As<IMailService>();
        }
    }
}
