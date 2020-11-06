namespace DrinkUp.Models.Common
{
    public interface IKorisnikReset
    {
        int Id { get; set; }
        int KorisnikId { get; set; }
        string KodId { get; set; }

        IKodModel Kod { get; set; }
        IKorisnikModel Korisnik { get; set; }
    }
}