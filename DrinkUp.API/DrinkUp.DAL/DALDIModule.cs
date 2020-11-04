using Autofac;
using DrinkUp.DAL.Context;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.DAL
{
    public class DALDIModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder.RegisterType<DrinkUpContext>().As<IDrinkContext>();
        }
    }
}
