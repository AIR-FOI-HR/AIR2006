using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;
using DrinkUp.DAL.Entities;
using System.Threading.Tasks;

namespace DrinkUp.DAL.Context
{
    public partial class DrinkUpContext : DbContext, IDrinkContext
    {
        public DrinkUpContext()
        {
        }

        public DrinkUpContext(DbContextOptions<DrinkUpContext> options)
            : base(options)
        {
        }

        public virtual DbSet<AktivacijaObjekta> AktivacijaObjekta { get; set; }
        public virtual DbSet<Kod> Kod { get; set; }
        public virtual DbSet<Korisnik> Korisnik { get; set; }
        public virtual DbSet<KorisnikAktivacija> KorisnikAktivacija { get; set; }
        public virtual DbSet<KorisnikReset> KorisnikReset { get; set; }
        public virtual DbSet<Objekt> Objekt { get; set; }
        public virtual DbSet<Ponuda> Ponuda { get; set; }
        public virtual DbSet<Token> Token { get; set; }
        public virtual DbSet<Uloga> Uloga { get; set; }
        public virtual DbSet<VrstaPonude> VrstaPonude { get; set; }
        public virtual DbSet<ZaposlenikObjekt> ZaposlenikObjekt { get; set; }

        public async Task<int> SaveChangesAsync()
        {
            return await base.SaveChangesAsync();
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseSqlServer("Server=tcp:air2006.database.windows.net,1433;Database=air2006sql;User ID=kacko;Password=tri*SEDAM=21;Trusted_Connection=False;");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<AktivacijaObjekta>(entity =>
            {
                entity.Property(e => e.KodId)
                    .IsRequired()
                    .HasMaxLength(65)
                    .IsUnicode(false);

                entity.HasOne(d => d.Kod)
                    .WithMany(p => p.AktivacijaObjekta)
                    .HasForeignKey(d => d.KodId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("AktivacijaObjekta_FK_1");

                entity.HasOne(d => d.Objekt)
                    .WithMany(p => p.AktivacijaObjekta)
                    .HasForeignKey(d => d.ObjektId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("AktivacijaObjekta_FK");
            });

            modelBuilder.Entity<Kod>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasMaxLength(65)
                    .IsUnicode(false);

                entity.Property(e => e.DatumKreiranja)
                    .HasColumnType("datetime")
                    .HasDefaultValueSql("(getdate())");
            });

            modelBuilder.Entity<Korisnik>(entity =>
            {
                entity.HasIndex(e => e.Email)
                    .HasName("Korisnik_UN1")
                    .IsUnique();

                entity.HasIndex(e => e.Oib)
                    .HasName("Korisnik_UN")
                    .IsUnique();

                entity.Property(e => e.Email)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.Ime)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.Lozinka)
                    .HasMaxLength(65)
                    .IsUnicode(false);

                entity.Property(e => e.Oib)
                    .IsRequired()
                    .HasColumnName("OIB")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.Prezime)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.HasOne(d => d.Uloga)
                    .WithMany(p => p.Korisnik)
                    .HasForeignKey(d => d.UlogaId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Korisnik_FK");
            });

            modelBuilder.Entity<KorisnikAktivacija>(entity =>
            {
                entity.Property(e => e.KodId)
                    .IsRequired()
                    .HasMaxLength(65)
                    .IsUnicode(false);

                entity.HasOne(d => d.Kod)
                    .WithMany(p => p.KorisnikAktivacija)
                    .HasForeignKey(d => d.KodId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("KorisnikAktivacija_FK_1");

                entity.HasOne(d => d.Korisnik)
                    .WithMany(p => p.KorisnikAktivacija)
                    .HasForeignKey(d => d.KorisnikId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("KorisnikAktivacija_FK");
            });

            modelBuilder.Entity<KorisnikReset>(entity =>
            {
                entity.Property(e => e.KodId)
                    .IsRequired()
                    .HasMaxLength(65)
                    .IsUnicode(false);

                entity.HasOne(d => d.Kod)
                    .WithMany(p => p.KorisnikReset)
                    .HasForeignKey(d => d.KodId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("KorisnikReset_FK_1");

                entity.HasOne(d => d.Korisnik)
                    .WithMany(p => p.KorisnikReset)
                    .HasForeignKey(d => d.KorisnikId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("KorisnikReset_FK");
            });

            modelBuilder.Entity<Objekt>(entity =>
            {
                entity.Property(e => e.Adresa)
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.Grad)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.Kontakt)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.Naziv)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.RadnoVrijeme)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.Ulica)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Ponuda>(entity =>
            {
                entity.HasIndex(e => e.Naslov)
                    .HasName("Ponuda_UN")
                    .IsUnique();

                entity.Property(e => e.Naslov)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.Opis)
                    .IsRequired()
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.HasOne(d => d.Objekt)
                    .WithMany(p => p.Ponuda)
                    .HasForeignKey(d => d.ObjektId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Ponuda_FK_1");

                entity.HasOne(d => d.VrstaPonude)
                    .WithMany(p => p.Ponuda)
                    .HasForeignKey(d => d.VrstaPonudeId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Ponuda_FK");
            });

            modelBuilder.Entity<Token>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasMaxLength(60)
                    .IsUnicode(false);

                entity.Property(e => e.DatumKreiranja)
                    .HasColumnType("datetime")
                    .HasDefaultValueSql("(getdate())");

                entity.Property(e => e.Qr)
                    .HasColumnName("QR")
                    .HasColumnType("image");

                entity.HasOne(d => d.Korisnik)
                    .WithMany(p => p.Token)
                    .HasForeignKey(d => d.KorisnikId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Token_FK_1");

                entity.HasOne(d => d.Ponuda)
                    .WithMany(p => p.Token)
                    .HasForeignKey(d => d.PonudaId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Token_FK");
            });

            modelBuilder.Entity<Uloga>(entity =>
            {
                entity.HasIndex(e => e.Naziv)
                    .HasName("Uloga_UN")
                    .IsUnique();

                entity.Property(e => e.Naziv)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<VrstaPonude>(entity =>
            {
                entity.HasIndex(e => e.Naziv)
                    .HasName("VrstaPonude_UN")
                    .IsUnique();

                entity.Property(e => e.Naziv)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<ZaposlenikObjekt>(entity =>
            {
                entity.HasOne(d => d.Korisnik)
                    .WithMany(p => p.ZaposlenikObjekt)
                    .HasForeignKey(d => d.KorisnikId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("ZaposlenikObjekt_FK_1");

                entity.HasOne(d => d.Objekt)
                    .WithMany(p => p.ZaposlenikObjekt)
                    .HasForeignKey(d => d.ObjektId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("ZaposlenikObjekt_FK");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
