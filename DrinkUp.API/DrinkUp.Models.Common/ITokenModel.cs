using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface ITokenModel
    {
        string Id { get; set; }
        int PonudaId { get; set; }

        IPonudaModel Ponuda { get; set; }
        ICollection<IKorisnikTokenModel> KorisnikToken { get; set; }
    }
}
