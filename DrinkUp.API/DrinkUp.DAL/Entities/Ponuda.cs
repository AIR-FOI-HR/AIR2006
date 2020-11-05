using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class Ponuda
    {
        public Ponuda()
        {
            ObjektPonuda = new HashSet<ObjektPonuda>();
            Token = new HashSet<Token>();
        }

        public int Id { get; set; }
        public string Naslov { get; set; }
        public string Opis { get; set; }
        public double Cijena { get; set; }
        public int BrojTokena { get; set; }
        public int VrstaPonudeId { get; set; }

        public virtual VrstaPonude VrstaPonude { get; set; }
        public virtual ICollection<ObjektPonuda> ObjektPonuda { get; set; }
        public virtual ICollection<Token> Token { get; set; }
    }
}
