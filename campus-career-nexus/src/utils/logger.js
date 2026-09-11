/**
 * 日志系统
 * 用于记录应用运行日志和问题追踪
 */

const LOG_LEVELS = {
  DEBUG: 'DEBUG',
  INFO: 'INFO',
  WARN: 'WARN',
  ERROR: 'ERROR'
};

const LOG_COLORS = {
  DEBUG: '#6b7280',
  INFO: '#3b82f6',
  WARN: '#f59e0b',
  ERROR: '#ef4444'
};

class Logger {
  constructor() {
    this.logs = [];
    this.issues = [];
  }

  /**
   * 格式化时间戳
   */
  getTimestamp() {
    return new Date().toISOString();
  }

  /**
   * 记录日志
   */
  log(level, message, data = null) {
    const logEntry = {
      timestamp: this.getTimestamp(),
      level,
      message,
      data,
      stack: level === LOG_LEVELS.ERROR ? new Error().stack : null
    };

    this.logs.push(logEntry);

    // 控制台输出
    const color = LOG_COLORS[level] || '#000000';
    console.log(
      `%c[${level}] %c${logEntry.timestamp} %c${message}`,
      `color: ${color}; font-weight: bold`,
      'color: #6b7280',
      'color: inherit'
    );

    if (data) {
      console.log('Data:', data);
    }

    return logEntry;
  }

  debug(message, data) {
    return this.log(LOG_LEVELS.DEBUG, message, data);
  }

  info(message, data) {
    return this.log(LOG_LEVELS.INFO, message, data);
  }

  warn(message, data) {
    return this.log(LOG_LEVELS.WARN, message, data);
  }

  error(message, data) {
    return this.log(LOG_LEVELS.ERROR, message, data);
  }

  /**
   * 记录问题
   */
  logIssue(issue) {
    const issueEntry = {
      id: `ISSUE-${Date.now()}`,
      timestamp: this.getTimestamp(),
      status: 'OPEN',
      ...issue
    };

    this.issues.push(issueEntry);
    console.warn(`%c[ISSUE] ${issueEntry.id}: ${issue.title}`, 'color: #f59e0b; font-weight: bold');

    return issueEntry;
  }

  /**
   * 更新问题状态
   */
  updateIssueStatus(issueId, status, resolution = null) {
    const issue = this.issues.find(i => i.id === issueId);
    if (issue) {
      issue.status = status;
      issue.resolution = resolution;
      issue.resolvedAt = status === 'RESOLVED' ? this.getTimestamp() : null;
    }
    return issue;
  }

  /**
   * 获取所有日志
   */
  getLogs(level = null) {
    if (level) {
      return this.logs.filter(log => log.level === level);
    }
    return this.logs;
  }

  /**
   * 获取所有问题
   */
  getIssues(status = null) {
    if (status) {
      return this.issues.filter(issue => issue.status === status);
    }
    return this.issues;
  }

  /**
   * 导出日志为JSON
   */
  exportLogs() {
    return JSON.stringify({
      logs: this.logs,
      issues: this.issues,
      exportedAt: this.getTimestamp()
    }, null, 2);
  }

  /**
   * 清空日志
   */
  clearLogs() {
    this.logs = [];
    this.issues = [];
  }
}

// 创建单例实例
const logger = new Logger();

export default logger;
export { LOG_LEVELS };
