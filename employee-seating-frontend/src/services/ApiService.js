export const ApiService = {
  async request(url, method = 'GET', data = null) {
    const headers = {
      'Content-Type': 'application/json',
    };

    const options = {
      method,
      headers,
    };

    if (data) {
      options.body = JSON.stringify(data);
    }

    try {
      const response = await fetch(url, options);
      const contentType = response.headers.get('Content-Type');

      let result;
      if (contentType && contentType.includes('application/json')) {
        result = await response.json();
      } else {
        result = await response.text();
      }

      if (!response.ok) {
        throw {
          status: response.status,
          message: result?.message || 'API Error',
          data: result,
        };
      }

      return result;
    } catch (error) {
      console.error('APIService Error:', error);
      throw error;
    }
  },

  get(url) {
    return this.request(url, 'GET');
  },

  post(url, data) {
    return this.request(url, 'POST', data);
  },

  put(url, data) {
    return this.request(url, 'PUT', data);
  },

  delete(url) {
    return this.request(url, 'DELETE');
  },
};

export default ApiService;