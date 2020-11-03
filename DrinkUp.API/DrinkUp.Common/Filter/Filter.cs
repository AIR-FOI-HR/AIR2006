using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Common.Filter
{
    public enum FilterOptions
    {
        StartsWith = 1,
        EndsWith,
        Contains,
        DoesNotContain,
        IsEmpty,
        IsNotEmpty,
        IsGreaterThan,
        IsGreaterThanOrEqualTo,
        IsLessThan,
        IsLessThanOrEqualTo,
        IsEqualTo,
        IsNotEqualTo
    }

    public class FilterParams
    {
        public string ColumnName { get; set; } = string.Empty;
        public string FilterValue { get; set; } = string.Empty;
        public FilterOptions FilterOption { get; set; } = FilterOptions.Contains;
    }

    public class Filter : IFilter
    {
        public IQueryable<T> FilteredData<T>(IQueryable<T> data, IEnumerable<FilterParams> filterParams)
        {
            if (filterParams == null)
            {
                return data;
            }

            IEnumerable<string> distinctColumns = filterParams.Where(x => !string.IsNullOrEmpty(x.ColumnName)).Select(x => x.ColumnName).Distinct();

            foreach (string colName in distinctColumns)
            {
                PropertyInfo filterColumn = typeof(T).GetProperty(colName, BindingFlags.IgnoreCase | BindingFlags.Instance | BindingFlags.Public);

                if (filterColumn != null)
                {
                    IEnumerable<FilterParams> filterValues = filterParams.Where(x => x.ColumnName.Equals(colName)).Distinct();

                    if (filterValues.Count() > 1)
                    {
                        IQueryable<T> sameColData = Queryable.DefaultIfEmpty(data);

                        foreach (var val in filterValues)
                        {
                            sameColData = sameColData.Concat(FilterData(val.FilterOption, data, filterColumn, val.FilterValue));
                        }

                        data = data.Intersect(sameColData);
                    }
                    else
                    {
                        data = FilterData(filterValues.FirstOrDefault().FilterOption, data, filterColumn, filterValues.FirstOrDefault().FilterValue);
                    }
                }
            }
            return data;
        }

        private IQueryable<T> FilterData<T>(FilterOptions filterOption, IQueryable<T> data, PropertyInfo filterColumn, string filterValue)
        {
            int outValue;
            DateTime dateValue;

            switch (filterOption)
            {
                #region [StringDataType]  

                case FilterOptions.StartsWith:
                    data = data.Where(x => EF.Property<string>(x, filterColumn.Name) != null && EF.Property<string>(x, filterColumn.Name).ToLower().StartsWith(filterValue.ToString().ToLower()));
                    break;
                case FilterOptions.EndsWith:
                    data = data.Where(x => EF.Property<string>(x, filterColumn.Name) != null && EF.Property<string>(x, filterColumn.Name).ToLower().EndsWith(filterValue.ToString().ToLower()));
                    break;
                case FilterOptions.Contains:
                    data = data.Where(x => EF.Property<string>(x, filterColumn.Name) != null && EF.Property<string>(x, filterColumn.Name).ToLower().Contains(filterValue.ToString().ToLower()));
                    break;
                case FilterOptions.DoesNotContain:
                    data = data.Where(x => EF.Property<string>(x, filterColumn.Name) == null ||
                                     (EF.Property<string>(x, filterColumn.Name) != null && !EF.Property<string>(x, filterColumn.Name).ToLower().Contains(filterValue.ToString().ToLower())));
                    break;
                case FilterOptions.IsEmpty:
                    data = data.Where(x => EF.Property<string>(x, filterColumn.Name) == null ||
                                     (EF.Property<string>(x, filterColumn.Name) != null && EF.Property<string>(x, filterColumn.Name) == string.Empty));
                    break;
                case FilterOptions.IsNotEmpty:
                    data = data.Where(x => EF.Property<string>(x, filterColumn.Name) != null && EF.Property<string>(x, filterColumn.Name) != string.Empty);
                    break;
                #endregion

                #region [Custom]  

                case FilterOptions.IsGreaterThan:
                    if ((filterColumn.PropertyType == typeof(Int32) || filterColumn.PropertyType == typeof(Nullable<Int32>)) && Int32.TryParse(filterValue, out outValue))
                    {
                        data = data.Where(x => EF.Property<int>(x, filterColumn.Name) > outValue);
                    }
                    else if ((filterColumn.PropertyType == typeof(Nullable<DateTime>)) && DateTime.TryParse(filterValue, out dateValue))
                    {
                        data = data.Where(x => EF.Property<DateTime>(x, filterColumn.Name) > dateValue);

                    }
                    break;

                case FilterOptions.IsGreaterThanOrEqualTo:
                    if ((filterColumn.PropertyType == typeof(Int32) || filterColumn.PropertyType == typeof(Nullable<Int32>)) && Int32.TryParse(filterValue, out outValue))
                    {
                        data = data.Where(x => EF.Property<int>(x, filterColumn.Name) >= outValue);
                    }
                    else if ((filterColumn.PropertyType == typeof(Nullable<DateTime>)) && DateTime.TryParse(filterValue, out dateValue))
                    {
                        data = data.Where(x => EF.Property<DateTime>(x, filterColumn.Name) >= dateValue);
                        break;
                    }
                    break;

                case FilterOptions.IsLessThan:
                    if ((filterColumn.PropertyType == typeof(Int32) || filterColumn.PropertyType == typeof(Nullable<Int32>)) && Int32.TryParse(filterValue, out outValue))
                    {
                        data = data.Where(x => EF.Property<int>(x, filterColumn.Name) < outValue);
                    }
                    else if ((filterColumn.PropertyType == typeof(Nullable<DateTime>)) && DateTime.TryParse(filterValue, out dateValue))
                    {
                        data = data.Where(x => EF.Property<DateTime>(x, filterColumn.Name) < dateValue);
                        break;
                    }
                    break;

                case FilterOptions.IsLessThanOrEqualTo:
                    if ((filterColumn.PropertyType == typeof(Int32) || filterColumn.PropertyType == typeof(Nullable<Int32>)) && Int32.TryParse(filterValue, out outValue))
                    {
                        data = data.Where(x => EF.Property<int>(x, filterColumn.Name) <= outValue);
                    }
                    else if ((filterColumn.PropertyType == typeof(Nullable<DateTime>)) && DateTime.TryParse(filterValue, out dateValue))
                    {
                        data = data.Where(x => EF.Property<DateTime>(x, filterColumn.Name) <= dateValue);
                        break;
                    }
                    break;

                case FilterOptions.IsEqualTo:
                    if (filterValue == string.Empty)
                    {
                        data = data.Where(x => EF.Property<object>(x, filterColumn.Name) == null
                                        || (EF.Property<object>(x, filterColumn.Name) != null && EF.Property<string>(x, filterColumn.Name).ToString().ToLower() == string.Empty));
                    }
                    else
                    {
                        if ((filterColumn.PropertyType == typeof(Int32) || filterColumn.PropertyType == typeof(Nullable<Int32>)) && Int32.TryParse(filterValue, out outValue))
                        {
                            data = data.Where(x => EF.Property<int>(x, filterColumn.Name) == outValue);
                        }
                        else if ((filterColumn.PropertyType == typeof(Nullable<DateTime>)) && DateTime.TryParse(filterValue, out dateValue))
                        {
                            data = data.Where(x => EF.Property<DateTime>(x, filterColumn.Name) == dateValue);
                            break;
                        }
                        else
                        {
                            data = data.Where(x => EF.Property<object>(x, filterColumn.Name) != null && EF.Property<string>(x, filterColumn.Name).ToString().ToLower() == filterValue.ToLower());
                        }
                    }
                    break;

                case FilterOptions.IsNotEqualTo:
                    if ((filterColumn.PropertyType == typeof(Int32) || filterColumn.PropertyType == typeof(Nullable<Int32>)) && Int32.TryParse(filterValue, out outValue))
                    {
                        data = data.Where(x => EF.Property<int>(x, filterColumn.Name) != outValue);
                    }
                    else if ((filterColumn.PropertyType == typeof(Nullable<DateTime>)) && DateTime.TryParse(filterValue, out dateValue))
                    {
                        data = data.Where(x => EF.Property<DateTime>(x, filterColumn.Name) != dateValue);
                        break;
                    }
                    else
                    {
                        data = data.Where(x => EF.Property<object>(x, filterColumn.Name) == null ||
                                         (EF.Property<object>(x, filterColumn.Name) != null && EF.Property<string>(x, filterColumn.Name).ToLower() != filterValue.ToLower()));
                    }
                    break;
                    #endregion
            }
            return data;
        }
    }
}
