// 公共JavaScript函数

// AJAX请求封装
function ajaxRequest(url, method, data, successCallback, errorCallback) {
    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(response) {
            if (response.code === 200) {
                if (successCallback) {
                    successCallback(response);
                }
            } else {
                if (errorCallback) {
                    errorCallback(response);
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '失败',
                        text: response.message
                    });
                }
            }
        },
        error: function(xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: '错误',
                text: '请求失败，请稍后重试'
            });
        }
    });
}

// 格式化金额
function formatMoney(amount) {
    return '¥' + amount.toFixed(2);
}

// 格式化时间
function formatTime(timestamp) {
    const date = new Date(timestamp);
    return date.getFullYear() + '-' +
        String(date.getMonth() + 1).padStart(2, '0') + '-' +
        String(date.getDate()).padStart(2, '0') + ' ' +
        String(date.getHours()).padStart(2, '0') + ':' +
        String(date.getMinutes()).padStart(2, '0');
}

// 显示加载中
function showLoading() {
    Swal.fire({
        title: '加载中...',
        didOpen: () => {
            Swal.showLoading();
        },
        allowOutsideClick: false
    });
}

// 隐藏加载中
function hideLoading() {
    Swal.close();
}

// 确认对话框
function confirmDialog(title, text, callback) {
    Swal.fire({
        title: title,
        text: text,
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '确定',
        cancelButtonText: '取消'
    }).then((result) => {
        if (result.isConfirmed) {
            callback();
        }
    });
}

// 成功提示
function successAlert(title, text) {
    Swal.fire({
        icon: 'success',
        title: title,
        text: text,
        timer: 1500
    });
}

// 错误提示
function errorAlert(title, text) {
    Swal.fire({
        icon: 'error',
        title: title,
        text: text
    });
}

// 复制到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(function() {
        successAlert('成功', '已复制到剪贴板');
    }, function() {
        errorAlert('失败', '复制失败');
    });
}

// 获取URL参数
function getUrlParam(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

// 设置URL参数
function setUrlParam(name, value) {
    const url = new URL(window.location.href);
    url.searchParams.set(name, value);
    window.history.replaceState({}, '', url);
}

// 删除URL参数
function removeUrlParam(name) {
    const url = new URL(window.location.href);
    url.searchParams.delete(name);
    window.history.replaceState({}, '', url);
}

// 验证手机号
function isValidPhone(phone) {
    return /^1[3-9]\d{9}$/.test(phone);
}

// 验证邮箱
function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

// 验证身份证号
function isValidIdCard(idCard) {
    return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idCard);
}

// 数组去重
function uniqueArray(arr) {
    return [...new Set(arr)];
}

// 数组求和
function sumArray(arr) {
    return arr.reduce((sum, item) => sum + item, 0);
}

// 数组求平均
function avgArray(arr) {
    return sumArray(arr) / arr.length;
}

// 数组最大值
function maxArray(arr) {
    return Math.max(...arr);
}

// 数组最小值
function minArray(arr) {
    return Math.min(...arr);
}

// 节流函数
function throttle(func, delay) {
    let timer = null;
    return function() {
        if (!timer) {
            timer = setTimeout(() => {
                func.apply(this, arguments);
                timer = null;
            }, delay);
        }
    };
}

// 防抖函数
function debounce(func, delay) {
    let timer = null;
    return function() {
        if (timer) {
            clearTimeout(timer);
        }
        timer = setTimeout(() => {
            func.apply(this, arguments);
        }, delay);
    };
}

// 页面加载完成后执行
$(document).ready(function() {
    console.log('OpenMall loaded successfully');
});

// 页面滚动事件
$(window).scroll(function() {
    const scrollTop = $(this).scrollTop();
    if (scrollTop > 100) {
        $('.back-to-top').fadeIn();
    } else {
        $('.back-to-top').fadeOut();
    }
});

// 返回顶部
$('.back-to-top').click(function() {
    $('html, body').animate({scrollTop: 0}, 500);
});
