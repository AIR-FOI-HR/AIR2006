﻿using DrinkUp.DAL.Entities;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IKorisnikModel
    {
        int Id { get; set; }
        string OIB { get; set; }
        string Ime { get; set; }
        string Prezime { get; set; }
        string Email { get; set; }
        Spol Spol { get; set; }
        int UlogaId { get; set; }
        byte Aktivan { get; set; }

        IUlogaModel Uloga { get; set; }
        ICollection<IKorisnikTokenModel> KorisnikToken { get; set; }
        ICollection<IZaposlenikObjektModel> ZaposlenikObjekt { get; set; }
    }
}
