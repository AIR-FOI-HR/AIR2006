using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;
using DrinkUp.DAL.Entities;

namespace DrinkUp.DAL.Context
{
    public partial class DrinkUpContext : DbContext
    {
        public DrinkUpContext()
        {
        }

        public DrinkUpContext(DbContextOptions<DrinkUpContext> options)
            : base(options)
        {
        }

        public virtual DbSet<KorisnikTokenEntity> Korisnik { get; set; }
        public virtual DbSet<KorisnikTokenEntity> KorisnikToken { get; set; }
        public virtual DbSet<ObjektEntity> Objekt { get; set; }
        public virtual DbSet<ObjektPonudaEntity> ObjektPonuda { get; set; }
        public virtual DbSet<PonudaEntity> Ponuda { get; set; }
        public virtual DbSet<TokenEntity> Token { get; set; }
        public virtual DbSet<UlogaEntity> Uloga { get; set; }
        public virtual DbSet<VrstaPonudeEntity> VrstaPonude { get; set; }
        public virtual DbSet<ZaposlenikObjektEntity> ZaposlenikObjekt { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http://go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
                optionsBuilder.UseSqlServer("Server=tcp:air2006.database.windows.net,1433;Database=air2006sql;User ID=kacko;Password=tri*SEDAM=21;Trusted_Connection=False;");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<KorisnikTokenEntity>(entity =>
            {
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

            modelBuilder.Entity<KorisnikTokenEntity>(entity =>
            {
                entity.Property(e => e.TokenId)
                    .IsRequired()
                    .HasMaxLength(60)
                    .IsUnicode(false);

                entity.HasOne(d => d.Korisnik)
                    .WithMany(p => p.KorisnikToken)
                    .HasForeignKey(d => d.KorisnikId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("KorisnikToken_FK");

                entity.HasOne(d => d.Token)
                    .WithMany(p => p.KorisnikToken)
                    .HasForeignKey(d => d.TokenId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("KorisnikToken_FK_1");
            });

            modelBuilder.Entity<ObjektEntity>(entity =>
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

            modelBuilder.Entity<ObjektPonudaEntity>(entity =>
            {
                entity.HasOne(d => d.Objekt)
                    .WithMany(p => p.ObjektPonuda)
                    .HasForeignKey(d => d.ObjektId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("ObjektPonuda_FK");

                entity.HasOne(d => d.Ponuda)
                    .WithMany(p => p.ObjektPonuda)
                    .HasForeignKey(d => d.PonudaId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("ObjektPonuda_FK_1");
            });

            modelBuilder.Entity<PonudaEntity>(entity =>
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

                entity.HasOne(d => d.VrstaPonude)
                    .WithMany(p => p.Ponuda)
                    .HasForeignKey(d => d.VrstaPonudeId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Ponuda_FK");
            });

            modelBuilder.Entity<TokenEntity>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasMaxLength(60)
                    .IsUnicode(false);

                entity.HasOne(d => d.Ponuda)
                    .WithMany(p => p.Token)
                    .HasForeignKey(d => d.PonudaId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Token_FK");
            });

            modelBuilder.Entity<UlogaEntity>(entity =>
            {
                entity.HasIndex(e => e.Naziv)
                    .HasName("Uloga_UN")
                    .IsUnique();

                entity.Property(e => e.Naziv)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<VrstaPonudeEntity>(entity =>
            {
                entity.HasIndex(e => e.Naziv)
                    .HasName("VrstaPonude_UN")
                    .IsUnique();

                entity.Property(e => e.Naziv)
                    .IsRequired()
                    .HasMaxLength(45)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<ZaposlenikObjektEntity>(entity =>
            {
                entity.HasOne(d => d.Korisnik)
                    .WithMany(p => p.ZaposlenikObjekt)
                    .HasForeignKey(d => d.KorisnikId)
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
