export default class AppUtil {
  static getSearchObjectFromUrl = (urlQuery?: URLSearchParams) => {
    if (!urlQuery) urlQuery = new URLSearchParams(window?.location?.search);
    return Object.fromEntries(urlQuery);
  };
}
