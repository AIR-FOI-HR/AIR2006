-- MySQL Script generated by MySQL Workbench
-- Mon Nov  2 00:03:10 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Uloga`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Uloga` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Korisnik`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Korisnik` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `OIB` VARCHAR(20) NOT NULL,
  `ime` VARCHAR(45) NOT NULL,
  `prezime` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `lozinka` VARCHAR(65) NOT NULL,
  `spol` VARCHAR(45) NOT NULL,
  `Uloga_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `OIB_UNIQUE` (`OIB` ASC),
  INDEX `fk_Korisnik_Uloga_idx` (`Uloga_id` ASC),
  CONSTRAINT `fk_Korisnik_Uloga`
    FOREIGN KEY (`Uloga_id`)
    REFERENCES `mydb`.`Uloga` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`VrstaPonude`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`VrstaPonude` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `naziv_UNIQUE` (`naziv` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Ponuda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Ponuda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naslovPonude` VARCHAR(45) NOT NULL,
  `opisPonude` VARCHAR(255) NOT NULL,
  `cijena` FLOAT NOT NULL,
  `brojTokena` INT NOT NULL DEFAULT 0,
  `VrstaPonude_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `naslovPonude_UNIQUE` (`naslovPonude` ASC),
  INDEX `fk_Ponuda_VrstaPonude1_idx` (`VrstaPonude_id` ASC),
  CONSTRAINT `fk_Ponuda_VrstaPonude1`
    FOREIGN KEY (`VrstaPonude_id`)
    REFERENCES `mydb`.`VrstaPonude` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Token` (
  `id` VARCHAR(60) NOT NULL,
  `Ponuda_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Token_Ponuda1_idx` (`Ponuda_id` ASC),
  CONSTRAINT `fk_Token_Ponuda1`
    FOREIGN KEY (`Ponuda_id`)
    REFERENCES `mydb`.`Ponuda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Korisnik_Token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Korisnik_Token` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Korisnik_id` INT NOT NULL,
  `Token_id` VARCHAR(60) NOT NULL,
  INDEX `fk_Korisnik_Token_Korisnik1_idx` (`Korisnik_id` ASC),
  INDEX `fk_Korisnik_Token_Token1_idx` (`Token_id` ASC),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_Korisnik_Token_Korisnik1`
    FOREIGN KEY (`Korisnik_id`)
    REFERENCES `mydb`.`Korisnik` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Korisnik_Token_Token1`
    FOREIGN KEY (`Token_id`)
    REFERENCES `mydb`.`Token` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Objekt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Objekt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NOT NULL,
  `grad` VARCHAR(45) NOT NULL,
  `ulica` VARCHAR(45) NOT NULL,
  `adresa` VARCHAR(45) NOT NULL,
  `radnoVrijeme` VARCHAR(45) NOT NULL,
  `kontaktBroj` VARCHAR(45) NOT NULL,
  `yKoordinata` FLOAT(12,4) NOT NULL,
  `xKoordinata` FLOAT(12,4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Objekt_Ponuda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Objekt_Ponuda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Objekt_id` INT NOT NULL,
  `Ponuda_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Objekt_Ponuda_Objekt1_idx` (`Objekt_id` ASC),
  INDEX `fk_Objekt_Ponuda_Ponuda1_idx` (`Ponuda_id` ASC),
  CONSTRAINT `fk_Objekt_Ponuda_Objekt1`
    FOREIGN KEY (`Objekt_id`)
    REFERENCES `mydb`.`Objekt` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Objekt_Ponuda_Ponuda1`
    FOREIGN KEY (`Ponuda_id`)
    REFERENCES `mydb`.`Ponuda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Zaposlenik_Objekt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Zaposlenik_Objekt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Objekt_id` INT NOT NULL,
  `Korisnik_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Zaposlenik_Objekt_Objekt1_idx` (`Objekt_id` ASC),
  INDEX `fk_Zaposlenik_Objekt_Korisnik1_idx` (`Korisnik_id` ASC),
  CONSTRAINT `fk_Zaposlenik_Objekt_Objekt1`
    FOREIGN KEY (`Objekt_id`)
    REFERENCES `mydb`.`Objekt` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Zaposlenik_Objekt_Korisnik1`
    FOREIGN KEY (`Korisnik_id`)
    REFERENCES `mydb`.`Korisnik` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;