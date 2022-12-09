/**
 * 存储localStorage
 */
export const setStorage = (name, content) => {
  if (!name) return;
  if (typeof content !== 'string') {
    content = JSON.stringify(content);
  }
  window.localStorage.setItem(name, content);
};

/**
 * 获取localStorage
 */
export const getStorage = name => {
  if (!name) return;
  return window.localStorage.getItem(name);
};

/**
 * 获取localStorage
 */
export const getBooleanStorage = name => {
  if (!name) return false;
  let value = window.localStorage.getItem(name);
  let flag = value === "false" || value == void 0 ? false : true;
  return flag;
};

/**
 * 删除localStorage
 */
export const removeStorage = name => {
  if (!name) return;
  window.localStorage.removeItem(name);
};


/**
 * 存储sessionStorage
 */
export const setSessionStorage = (name, content) => {
  if (!name) return;
  if (typeof content !== 'string') {
    content = JSON.stringify(content);
  }
  window.sessionStorage.setItem(name, content);
};

/**
 * 获取sessionStorage
 */
export const getSessionStorage = name => {
  if (!name) return;
  return window.sessionStorage.getItem(name);
};

/**
 * 获取sessionStorage
 */
export const getSessionBooleanStorage = name => {
  if (!name) return false;
  let value = window.sessionStorage.getItem(name);
  let flag = value === "false" || value == void 0 ? false : true;
  return flag;
};

/**
 * 删除sessionStorage
 */
export const removeSessionStorage = name => {
  if (!name) return;
  window.sessionStorage.removeItem(name);
};
