using DrinkUp.DAL.Context;
using DrinkUp.DAL.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.DAL
{
    public class Test
    {
        public int MyProperty { get; set; } = 5;

        public string Get()
        {
            using (DrinkUpContext context = new DrinkUpContext())
            {
                return "huehue";
            }
        }
    }
}
