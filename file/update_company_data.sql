-- 完善企业数据：使用前端本地图片

-- 1. 阿里巴巴集团 (id=1, 待审核)
UPDATE t_company SET
  logo = '/company-images/alibaba.svg',
  license_url = '/company-images/license.jpg',
  industry = '互联网',
  scale = '10000人以上',
  nature = '上市公司',
  address = '浙江省杭州市余杭区文一西路969号',
  website = 'https://www.alibaba.com',
  description = '阿里巴巴集团是全球领先的电子商务公司，业务涵盖淘宝、天猫、阿里云、菜鸟网络等多个领域。集团成立于1999年，目前在全球拥有超过20万名员工。'
WHERE id = 1;

-- 2. 腾讯科技 (id=2, 已拒绝)
UPDATE t_company SET
  logo = '/company-images/tencent.svg',
  license_url = '/company-images/license.jpg',
  industry = '互联网',
  scale = '10000人以上',
  nature = '上市公司',
  address = '广东省深圳市南山区科技中一路腾讯大厦',
  website = 'https://www.tencent.com',
  description = '腾讯是中国领先的互联网增值服务提供商，旗下拥有微信、QQ、腾讯游戏、腾讯云等产品，服务覆盖社交、娱乐、金融、企业服务等多个领域。',
  audit_remark = '营业执照模糊，请重新上传清晰版本',
  audit_time = '2026-09-16 10:15:10'
WHERE id = 2;

-- 3. 字节跳动 (id=3, 已通过)
UPDATE t_company SET
  logo = '/company-images/bytedance.svg',
  license_url = '/company-images/license.jpg',
  industry = '互联网',
  scale = '10000人以上',
  nature = '民营企业',
  address = '北京市海淀区北三环西路甲18号',
  website = 'https://www.bytedance.com',
  description = '字节跳动是全球领先的短视频和信息分发平台，旗下拥有抖音、TikTok、今日头条、飞书等产品，在全球范围内拥有超过15亿用户。',
  audit_time = '2026-09-16 09:00:00',
  audit_remark = '审核通过'
WHERE id = 3;
