// 商家后台公共JavaScript函数 - 增强版

$(document).ready(function() {
    console.log('Merchant loaded successfully');
    
    // 初始化工具提示
    initializeTooltips();
    
    // 自动隐藏警告消息
    autoHideAlerts();
    
    // 初始化页面动画
    initializePageAnimations();
    
    // 初始化滚动效果
    initializeScrollEffects();
    
    // 初始化按钮波纹效果
    initializeRippleEffect();
    
    console.log('Enhanced merchant UI initialized');
});

// 初始化工具提示
function initializeTooltips() {
    $('[data-bs-toggle="tooltip"]').tooltip({
        animation: true,
        delay: { show: 200, hide: 100 }
    });
}

// 获取URL参数
function getUrlParameter(name) {
    name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

// 复制文本到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        successAlert('复制成功', '已复制到剪贴板');
    }).catch(err => {
        errorAlert('复制失败', '请手动复制');
        console.error('复制失败:', err);
    });
}

// 格式化相对时间
function formatRelativeTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`;
    return formatDate(dateStr);
}

// 自动隐藏警告消息
function autoHideAlerts() {
    setTimeout(function() {
        $('.alert-auto-hide').fadeOut('slow');
    }, 3000);
}

// 初始化页面动画
function initializePageAnimations() {
    // 为卡片添加渐入动画
    $('.card').each(function(index) {
        $(this).css('opacity', '0');
        $(this).css('transform', 'translateY(20px)');
        $(this).delay(index * 100).animate({
            opacity: 1,
            translateY: 0
        }, {
            duration: 500,
            easing: 'swing',
            step: function(now, fx) {
                if (fx.prop === 'translateY') {
                    $(this).css('transform', 'translateY(' + now + 'px)');
                }
            }
        });
    });
}

// 获取URL参数
function getUrlParameter(name) {
    name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

// 复制文本到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        successAlert('复制成功', '已复制到剪贴板');
    }).catch(err => {
        errorAlert('复制失败', '请手动复制');
        console.error('复制失败:', err);
    });
}

// 格式化相对时间
function formatRelativeTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`;
    return formatDate(dateStr);
}

// 初始化滚动效果
function initializeScrollEffects() {
    let ticking = false;
    
    function updateScrollEffects() {
        const scrolled = $(window).scrollTop();
        const rate = scrolled * -0.5;
        
        // 视差效果（可选）
        $('.parallax-bg').css('transform', 'translate3d(0, ' + rate + 'px, 0)');
        
        ticking = false;
    }
    
    $(window).scroll(function() {
        if (!ticking) {
            requestAnimationFrame(updateScrollEffects);
            ticking = true;
        }
    });
}

// 获取URL参数
function getUrlParameter(name) {
    name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

// 复制文本到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        successAlert('复制成功', '已复制到剪贴板');
    }).catch(err => {
        errorAlert('复制失败', '请手动复制');
        console.error('复制失败:', err);
    });
}

// 格式化相对时间
function formatRelativeTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`;
    return formatDate(dateStr);
}

// 初始化按钮波纹效果
function initializeRippleEffect() {
    $('.btn').on('click', function(e) {
        const btn = $(this);
        if (btn.find('.ripple').length === 0) {
            btn.css('position', 'relative');
            btn.css('overflow', 'hidden');
        }
        
        const ripple = $('<span class="ripple"></span>');
        const rect = this.getBoundingClientRect();
        const size = Math.max(rect.width, rect.height);
        const x = e.clientX - rect.left - size / 2;
        const y = e.clientY - rect.top - size / 2;
        
        ripple.css({
            width: size,
            height: size,
            left: x,
            top: y,
            position: 'absolute',
            borderRadius: '50%',
            background: 'rgba(255, 255, 255, 0.5)',
            transform: 'scale(0)',
            animation: 'ripple 0.6s linear',
            pointerEvents: 'none'
        });
        
        btn.append(ripple);
        
        setTimeout(() => {
            ripple.remove();
        }, 600);
    });
}

// 获取URL参数
function getUrlParameter(name) {
    name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

// 复制文本到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        successAlert('复制成功', '已复制到剪贴板');
    }).catch(err => {
        errorAlert('复制失败', '请手动复制');
        console.error('复制失败:', err);
    });
}

// 格式化相对时间
function formatRelativeTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`;
    return formatDate(dateStr);
}

// 添加CSS动画
const rippleCSS = `
@keyframes ripple {
    to {
        transform: scale(4);
        opacity: 0;
    }
}

.fade-in-up {
    animation: fadeInUp 0.6s ease-out;
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(30px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.pulse {
    animation: pulse 2s infinite;
}

@keyframes pulse {
    0% {
        box-shadow: 0 0 0 0 rgba(75, 108, 183, 0.4);
    }
    70% {
        box-shadow: 0 0 0 10px rgba(75, 108, 183, 0);
    }
    100% {
        box-shadow: 0 0 0 0 rgba(75, 108, 183, 0);
    }
}
`;

// 将CSS添加到页面
if (!$('#merchant-animations').length) {
    $('<style id="merchant-animations">' + rippleCSS + '</style>').appendTo('head');
}

// 获取当前店铺ID
function getShopId() {
    // TODO: 从Session或SecurityContext中获取
    return 1;
}

// 格式化日期
function formatDate(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// 格式化日期时间
function formatDateTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

// 商品状态文本
function getProductStatusText(status) {
    const statusMap = {
        0: '已下架',
        1: '已上架'
    };
    return statusMap[status] || '未知';
}

// 订单状态文本
function getOrderStatusText(status) {
    const statusMap = {
        1: '待付款',
        2: '待发货',
        3: '待收货',
        4: '已完成',
        5: '已取消',
        6: '售后中'
    };
    return statusMap[status] || '未知';
}

// 订单状态徽章样式
function getOrderStatusBadge(status) {
    const badgeMap = {
        1: 'bg-warning',
        2: 'bg-info',
        3: 'bg-primary',
        4: 'bg-success',
        5: 'bg-secondary',
        6: 'bg-danger'
    };
    return badgeMap[status] || 'bg-secondary';
}

// 商家状态文本
function getMerchantStatusText(status) {
    const statusMap = {
        0: '待审核',
        1: '已通过',
        2: '已拒绝',
        3: '已禁用'
    };
    return statusMap[status] || '未知';
}

// 优惠券状态文本
function getCouponStatusText(status) {
    const statusMap = {
        0: '未开始',
        1: '进行中',
        2: '已结束'
    };
    return statusMap[status] || '未知';
}

// 优惠券类型文本
function getCouponTypeText(type) {
    const typeMap = {
        1: '满减券',
        2: '折扣券',
        3: '现金券'
    };
    return typeMap[type] || '未知';
}

// 店铺类型文本
function getShopTypeText(type) {
    const typeMap = {
        1: '旗舰店',
        2: '专卖店',
        3: '专营店'
    };
    return typeMap[type] || '未知';
}

// 货币格式化
function formatCurrency(amount) {
    return '¥' + parseFloat(amount).toFixed(2);
}

// 库存警告检查
function checkStockWarning(stock, threshold) {
    threshold = threshold || 10;
    return stock <= threshold;
}

// 获取库存警告样式
function getStockWarningClass(stock, threshold) {
    return checkStockWarning(stock, threshold) ? 'text-danger' : '';
}

// 生成随机ID
function generateId() {
    return Date.now() + Math.random().toString(36).substr(2, 9);
}

// 图片上传预览
function previewImage(input, previewId) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            $(previewId).attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

// SKU规格解析
function parseSpecs(specsStr) {
    try {
        return JSON.parse(specsStr);
    } catch (e) {
        return {};
    }
}

// SKU规格格式化
function formatSpecs(specs) {
    if (typeof specs === 'string') {
        specs = parseSpecs(specs);
    }
    return Object.entries(specs).map(([key, value]) => `${key}:${value}`).join(', ');
}

// 计算SKU库存总和
function calculateTotalStock(skuList) {
    return skuList.reduce((sum, sku) => sum + (sku.stock || 0), 0);
}

// 计算SKU价格区间
function calculatePriceRange(skuList) {
    if (!skuList || skuList.length === 0) {
        return { min: 0, max: 0 };
    }
    
    const prices = skuList.map(sku => parseFloat(sku.price || 0));
    return {
        min: Math.min(...prices),
        max: Math.max(...prices)
    };
}

// 检查是否有SKU库存不足
function checkSkusStock(skuList) {
    return skuList.some(sku => sku.stock <= 0);
}

// 表单序列化为JSON
function serializeFormToJson(formId) {
    const form = document.getElementById(formId);
    const formData = new FormData(form);
    const json = {};
    
    formData.forEach((value, key) => {
        if (key.endsWith('[]')) {
            key = key.slice(0, -2);
            if (!json[key]) json[key] = [];
            json[key].push(value);
        } else {
            json[key] = value;
        }
    });
    
    return json;
}

// 深拷贝对象
function deepClone(obj) {
    return JSON.parse(JSON.stringify(obj));
}

// 数组去重
function uniqueArray(arr, key) {
    const seen = new Set();
    return arr.filter(item => {
        const val = key ? item[key] : item;
        if (seen.has(val)) {
            return false;
        }
        seen.add(val);
        return true;
    });
}

// 获取URL参数
function getUrlParameter(name) {
    name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

// 复制文本到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        successAlert('复制成功', '已复制到剪贴板');
    }).catch(err => {
        errorAlert('复制失败', '请手动复制');
        console.error('复制失败:', err);
    });
}

// 格式化相对时间
function formatRelativeTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`;
    return formatDate(dateStr);
}

// 节流函数
function throttle(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// 防抖函数
function debounce(func, wait, immediate) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            timeout = null;
            if (!immediate) func(...args);
        };
        const callNow = immediate && !timeout;
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
        if (callNow) func(...args);
    };
}

// 显示加载动画
function showLoading() {
    if ($('#loadingOverlay').length === 0) {
        $('body').append(`
            <div id="loadingOverlay" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; 
                 background: rgba(0,0,0,0.5); z-index: 9999; display: flex; justify-content: center; 
                 align-items: center;">
                <div style="background: white; padding: 20px; border-radius: 8px; text-align: center;">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                    <div style="margin-top: 10px;">加载中...</div>
                </div>
            </div>
        `);
    }
    $('#loadingOverlay').show();
}

// 隐藏加载动画
function hideLoading() {
    $('#loadingOverlay').hide();
}

// 成功提示
function successAlert(title, message, callback) {
    Swal.fire({
        title: title,
        text: message,
        icon: 'success',
        confirmButtonText: '确定'
    }).then((result) => {
        if (result.isConfirmed && typeof callback === 'function') {
            callback();
        }
    });
}

// 获取URL参数
function getUrlParameter(name) {
    name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

// 复制文本到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        successAlert('复制成功', '已复制到剪贴板');
    }).catch(err => {
        errorAlert('复制失败', '请手动复制');
        console.error('复制失败:', err);
    });
}

// 格式化相对时间
function formatRelativeTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`;
    return formatDate(dateStr);
}

// 错误提示
function errorAlert(title, message, callback) {
    Swal.fire({
        title: title,
        text: message,
        icon: 'error',
        confirmButtonText: '确定'
    }).then((result) => {
        if (result.isConfirmed && typeof callback === 'function') {
            callback();
        }
    });
}

// 获取URL参数
function getUrlParameter(name) {
    name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

// 复制文本到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        successAlert('复制成功', '已复制到剪贴板');
    }).catch(err => {
        errorAlert('复制失败', '请手动复制');
        console.error('复制失败:', err);
    });
}

// 格式化相对时间
function formatRelativeTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`;
    return formatDate(dateStr);
}

// 确认对话框
function confirmDialog(title, message, confirmCallback, cancelCallback) {
    Swal.fire({
        title: title,
        text: message,
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
    }).then((result) => {
        if (result.isConfirmed) {
            if (typeof confirmCallback === 'function') {
                confirmCallback();
            }
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            if (typeof cancelCallback === 'function') {
                cancelCallback();
            }
        }
    });
}

// 获取URL参数
function getUrlParameter(name) {
    name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

// 复制文本到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        successAlert('复制成功', '已复制到剪贴板');
    }).catch(err => {
        errorAlert('复制失败', '请手动复制');
        console.error('复制失败:', err);
    });
}

// 格式化相对时间
function formatRelativeTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`;
    return formatDate(dateStr);
}

// AJAX请求封装
function ajaxRequest(url, method, data, successCallback, errorCallback) {
    $.ajax({
        url: url,
        method: method,
        data: data,
        success: function(response) {
            if (typeof successCallback === 'function') {
                successCallback(response);
            }
        },
        error: function(xhr, status, error) {
            if (typeof errorCallback === 'function') {
                errorCallback(xhr, status, error);
            } else {
                errorAlert('请求失败', '网络请求出现错误，请稍后重试');
            }
        }
    });
}

// 获取URL参数
function getUrlParameter(name) {
    name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

// 复制文本到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        successAlert('复制成功', '已复制到剪贴板');
    }).catch(err => {
        errorAlert('复制失败', '请手动复制');
        console.error('复制失败:', err);
    });
}

// 格式化相对时间
function formatRelativeTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    
    const now = new Date();
    const diffMs = now - date;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    
    if (diffMins < 1) return '刚刚';
    if (diffMins < 60) return `${diffMins}分钟前`;
    if (diffHours < 24) return `${diffHours}小时前`;
    if (diffDays < 7) return `${diffDays}天前`;
    if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`;
    return formatDate(dateStr);
}
