using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;
using System.Text;

namespace DrinkUp.Models.Common
{
    [JsonConverter(typeof(StringEnumConverter))]
    public enum Spol
    {
        [EnumMember(Value = "Musko")]
        Musko,
        [EnumMember(Value = "Zensko")]
        Zensko
    }
}
