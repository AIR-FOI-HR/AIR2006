using DrinkUp.DAL.Entities;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class KorisnikModel : IKorisnikModel
    {
        public int Id { get; set; }
        public string OIB { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Email { get; set; }
        public Spol Spol { get; set; }
        public int UlogaId { get; set; }
        public byte Aktivan { get; set; }
        
        public IUlogaModel Uloga { get; set; }
        public ICollection<IKorisnikTokenModel> KorisnikToken { get; set; }
        public ICollection<IZaposlenikObjektModel> ZaposlenikObjekt { get; set; }
    }
}
