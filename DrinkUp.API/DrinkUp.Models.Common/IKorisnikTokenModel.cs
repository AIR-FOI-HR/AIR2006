using DrinkUp.DAL.Entities;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IKorisnikTokenModel
    {
        int Id { get; set; }
        int KorisnikId { get; set; }
        string TokenId { get; set; }

        IKorisnikModel Korisnik { get; set; }
        ITokenModel Token { get; set; }
    }
}
