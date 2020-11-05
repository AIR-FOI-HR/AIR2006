using Autofac;
using AutoMapper;
using DrinkUp.Common.Filter;
using DrinkUp.Common.Page;
using DrinkUp.Common.Sort;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.Modules
{
    public class WebAPIDIModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder.RegisterType<Mapper>().As<IMapper>();
            builder.RegisterType<Filter>().As<IFilter>();
            builder.RegisterType<Sort>().As<ISort>();
            builder.RegisterType<PagedResult>().As<IPagedResult>();
        }
    }
}
