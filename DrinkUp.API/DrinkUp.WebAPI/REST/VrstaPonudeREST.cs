﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.REST
{
    public class VrstaPonudeREST
    {
        public int Id { get; set; }
        public string Naziv { get; set; }

        public ICollection<PonudaREST> Ponuda { get; set; }
    }
}